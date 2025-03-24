package com.aaemu.login.service.impl;

import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CATestArs;
import com.aaemu.login.service.dto.packet.server.ACChallenge;
import com.aaemu.login.service.dto.packet.server.ACEnterOtp;
import com.aaemu.login.service.dto.packet.server.ACEnterPcCert;
import com.aaemu.login.service.dto.packet.server.ACShowArs;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.model.OneTimePassword;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@EnableScheduling
@Log4j2
public class ChallengeServiceImpl implements ChallengeService {
    private static final String MAX_PC_CERT = "Maximum PcCert number of attempts reached";
    private static final String MAX_OTP = "Maximum OTP number of attempts reached";
    private static final String INVALID_PACKET_QUEUE = "Invalid packet queue";
    private static final String BAD_ACCOUNT = "Bad account";
    private final Map<Channel, OneTimePassword> pcCertMap;
    private final Map<Channel, OneTimePassword> otpMap;
    private final Map<Channel, Account> accountMap;
    private final LoginService loginService;
    private final ByteBufUtil byteBufUtil;

    @Value("${login_server.auth.otp.active}")
    private boolean useOtp;

    @Value("${login_server.auth.otp.num}")
    private String otpNum;

    @Value("${login_server.auth.otp.max_attempts}")
    private int otpMaxTry;

    @Value("${login_server.auth.ars.active}")
    private boolean useArs;

    @Value("${login_server.auth.ars.num}")
    private String arsNum;

    @Value("${login_server.auth.ars.timeout}")
    private int arsTimeout;

    @Value("${login_server.auth.pc_cert.active}")
    private boolean usePcCert;

    @Value("${login_server.auth.pc_cert.num}")
    private String pcCertNum;

    @Value("${login_server.auth.pc_cert.max_attempts}")
    private int pcCertMaxTry;

    @Override
    public void send(Channel channel) {
        ACChallenge acChallenge = new ACChallenge();
        acChallenge.setSalt(0);
        for (int i = 0; i < 4; i++) {
            acChallenge.getCh().add(0);
        }
        channel.writeAndFlush(acChallenge.build(byteBufUtil));
    }

    @Override
    public void receive(CAChallengeResponse packet) {
        accountMap.get(packet.getChannel()).setPassword(packet.getPw());
        if (useOtp) {
            sendOtp(packet.getChannel());
        } else if (useArs) {
            sendArs(packet.getChannel());
        } else if (usePcCert) {
            sendPcCert(packet.getChannel());
        } else {
            passChallenge(packet.getChannel());
        }
    }

    @Override
    public void receive(CAOtpNumber packet) {
        if (!otpMap.containsKey(packet.getChannel())) {
            loginService.rejectLogin(packet.getChannel(), 14, INVALID_PACKET_QUEUE);
            return;
        }
        if (otpMap.get(packet.getChannel()).getNumber().equals(packet.getNumber())) {
            otpMap.remove(packet.getChannel());
            passChallenge(packet.getChannel());
        } else {
            sendOtp(packet.getChannel());
        }
    }

    @Override
    public void receive(CATestArs packet) {
        receive(new CAOtpNumber(packet));
    }

    @Override
    public void receive(CAPcCertNumber packet) {
        if (!pcCertMap.containsKey(packet.getChannel())) {
            loginService.rejectLogin(packet.getChannel(), 14, INVALID_PACKET_QUEUE);
            return;
        }
        if (pcCertMap.get(packet.getChannel()).getNumber().equals(packet.getNum())) {
            pcCertMap.remove(packet.getChannel());
            passChallenge(packet.getChannel());
        } else {
            sendPcCert(packet.getChannel());
        }
    }

    private void sendArs(Channel channel) {
        channel.writeAndFlush(new ACShowArs(arsNum, arsTimeout).build(byteBufUtil));
    }

    private void sendOtp(Channel channel) {
        if (!otpMap.containsKey(channel)) {
            otpMap.put(channel, new OneTimePassword(1, otpNum));    // TODO Send via email
            sendOtp(channel, 1);
            return;
        }
        OneTimePassword oneTimePassword = otpMap.get(channel);
        oneTimePassword.setCount(oneTimePassword.getCount() + 1);
        if (oneTimePassword.getCount() > otpMaxTry) {
            otpMap.remove(channel);
            loginService.rejectLogin(channel, 0, MAX_OTP);
            return;
        }
        sendOtp(channel, oneTimePassword.getCount());
    }

    private void sendPcCert(Channel channel) {
        if (!pcCertMap.containsKey(channel)) {
            pcCertMap.put(channel, new OneTimePassword(1, pcCertNum));    // TODO Send via email
            sendPcCert(channel, 1);
            return;
        }
        OneTimePassword oneTimePassword = pcCertMap.get(channel);
        oneTimePassword.setCount(oneTimePassword.getCount() + 1);
        if (oneTimePassword.getCount() > pcCertMaxTry) {
            pcCertMap.remove(channel);
            loginService.rejectLogin(channel, 0, MAX_PC_CERT);
            return;
        }
        sendPcCert(channel, oneTimePassword.getCount());
    }

    private void sendOtp(Channel channel, int count) {
        channel.writeAndFlush(new ACEnterOtp(otpMaxTry, count).build(byteBufUtil));
    }

    private void sendPcCert(Channel channel, int count) {
        channel.writeAndFlush(new ACEnterPcCert(otpMaxTry, count).build(byteBufUtil));
    }

    private void passChallenge(Channel channel) {
        Account account = accountMap.get(channel);
        if (!loginService.isPresentAccount(account)) {
            loginService.rejectWarnedAccount(channel, 0, BAD_ACCOUNT);
            return;
        }
        if (loginService.isActiveAccount(account)) {
            loginService.allowLogin(channel);
            return;
        }
        loginService.rejectWarnedAccount(channel, 1, BAD_ACCOUNT);
    }

    @Scheduled(fixedRateString = "${login_server.auth.otp.clean_time}")
    private void cleanOtpMap() {
        for (Map.Entry<Channel, OneTimePassword> entry : otpMap.entrySet()) {
            if (!accountMap.containsKey(entry.getKey())) {
                otpMap.remove(entry.getKey());
            }
        }
    }

    @Scheduled(fixedRateString = "${login_server.auth.pc_cert.clean_time}")
    private void cleanPcCertMap() {
        for (Map.Entry<Channel, OneTimePassword> entry : pcCertMap.entrySet()) {
            if (!accountMap.containsKey(entry.getKey())) {
                pcCertMap.remove(entry.getKey());
            }
        }
    }
}
