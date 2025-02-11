package com.aaemu.login.service.impl;

import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.server.ACChallenge2;
import com.aaemu.login.service.dto.packet.server.ACEnterOtp;
import com.aaemu.login.service.dto.packet.server.ACEnterPcCert;
import com.aaemu.login.service.dto.packet.server.ACShowArs;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.model.OneTimePassword;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Log4j2
public class ChallengeServiceImpl implements ChallengeService {
    private final Map<Channel, OneTimePassword> pcCertMap;
    private final Map<Channel, OneTimePassword> otpMap;
    private final Map<Channel, Account> accountMap;
    private final LoginService loginService;
    private final ByteBufUtils byteBufUtils;

    @Value("${login_server.auth.otp.active}")
    private boolean useOtp;

    @Value("${login_server.auth.otp.max_attempts}")
    private int otpMaxTry;

    @Value("${login_server.auth.ars.active}")
    private boolean useArs;

    @Value("${login_server.auth.pc_cert.active}")
    private boolean usePcCert;

    @Value("${login_server.auth.pc_cert.max_attempts}")
    private int pcCertMaxTry;

    @Value("${login_server.auth.editor_mode.active}")
    private boolean isEditorMode;

    @Override
    public void challenge(CAChallengeResponse packet) {
        log.info("Password: {}, ch: {}, address: {}", packet.getPw(), packet.getCh(), packet.getChannel().remoteAddress().toString());
        accountMap.get(packet.getChannel()).setPassword(packet.getPw());
        accountMap.get(packet.getChannel()).setChallengeH1(packet.getCh());
        if (isEditorMode) {
            passChallenge(packet.getChannel());
        } else {
            ACChallenge2 acChallenge2 = new ACChallenge2();
            acChallenge2.setRound(0);
            acChallenge2.setSalt("");
            acChallenge2.setCh(0);
            packet.getChannel().writeAndFlush(acChallenge2.build(byteBufUtils));
        }
    }

    @Override
    public void challenge(CAChallengeResponse2 packet) {
        accountMap.get(packet.getChannel()).setChallengeH2(packet.getCh());
        if (useOtp) {
            sendOtp(packet.getChannel());
        } else if (useArs) {
            sendArs(packet);
        } else if (usePcCert) {
            sendPcCert(packet.getChannel());
        } else {
            passChallenge(packet.getChannel());
        }
    }

    @Override
    public void processOneTimePassword(CAOtpNumber packet) {
        if (!otpMap.containsKey(packet.getChannel())) {
            loginService.rejectLogin(packet.getChannel(), 14, "Invalid packet queue");
            return;
        }
        if (otpMap.get(packet.getChannel()).getPassword().equals(packet.getNum())) {
            otpMap.remove(packet.getChannel());
            passChallenge(packet.getChannel());
        } else {
            sendOtp(packet.getChannel());
        }
    }

    @Override
    public void processPcCertificate(CAPcCertNumber packet) {
        if (!pcCertMap.containsKey(packet.getChannel())) {
            loginService.rejectLogin(packet.getChannel(), 14, "Invalid packet queue");
            return;
        }
        if (pcCertMap.get(packet.getChannel()).getPassword().equals(packet.getNum())) {
            pcCertMap.remove(packet.getChannel());
            passChallenge(packet.getChannel());
        } else {
            sendPcCert(packet.getChannel());
        }
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

    private void sendArs(CAChallengeResponse2 packet) {
        packet.getChannel().writeAndFlush(new ACShowArs("1234", 10000).build(byteBufUtils));
    }

    private void sendOtp(Channel channel) {
        if (!otpMap.containsKey(channel)) {
            otpMap.put(channel, new OneTimePassword(1, "22222222"));    // TODO Send via email
            sendOtp(channel, 1);
            return;
        }
        OneTimePassword oneTimePassword = otpMap.get(channel);
        oneTimePassword.setCount(oneTimePassword.getCount() + 1);
        if (oneTimePassword.getCount() > otpMaxTry) {
            otpMap.remove(channel);
            loginService.rejectLogin(channel, 0, "Maximum OTP number of attempts reached");
            return;
        }
        sendOtp(channel, oneTimePassword.getCount());
    }

    private void sendPcCert(Channel channel) {
        if (!pcCertMap.containsKey(channel)) {
            pcCertMap.put(channel, new OneTimePassword(1, "22222222"));    // TODO Send via email
            sendPcCert(channel, 1);
            return;
        }
        OneTimePassword oneTimePassword = pcCertMap.get(channel);
        oneTimePassword.setCount(oneTimePassword.getCount() + 1);
        if (oneTimePassword.getCount() > pcCertMaxTry) {
            pcCertMap.remove(channel);
            loginService.rejectLogin(channel, 0, "Maximum PcCert number of attempts reached");
            return;
        }
        sendPcCert(channel, oneTimePassword.getCount());
    }

    private void sendOtp(Channel channel, int count) {
        channel.writeAndFlush(new ACEnterOtp(otpMaxTry, count).build(byteBufUtils));
    }

    private void sendPcCert(Channel channel, int count) {
        channel.writeAndFlush(new ACEnterPcCert(otpMaxTry, count).build(byteBufUtils));
    }

    private void passChallenge(Channel channel) {
        if (loginService.isValidAccount(channel)) {
            loginService.allowLogin(channel);
        } else {
            loginService.rejectWarnedAccount(channel, 1, "Bad account");
        }
    }
}
