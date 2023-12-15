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
import com.aaemu.login.service.model.TempPassword;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {
    private final Map<Channel, TempPassword> pcCertMap;
    private final Map<Channel, TempPassword> otpMap;
    private final LoginService loginService;
    private final ByteBufUtil byteBufUtil;

    @Value("${auth.otp}")
    private boolean useOtp;

    @Value("${auth.otp_max_try}")
    private int otpMaxTry;

    @Value("${auth.ars}")
    private boolean useArs;

    @Value("${auth.pc_cert}")
    private boolean usePcCert;

    @Value("${auth.pc_cert_max_try}")
    private int pcCertMaxTry;

    private void updateMap(Channel channel, int trying, Map<Channel, TempPassword> map) {
        if (map.containsKey(channel)) {
            map.get(channel).setCount(trying);
        } else {
            map.put(channel, new TempPassword(trying, "12345678"));
        }
    }

    private void sendOtp(Channel channel, int trying) {
        if (trying > otpMaxTry) {
            otpMap.remove(channel);
            loginService.rejectLogin(channel, 0, "Maximum OTP number of attempts reached");
            return;
        }
        updateMap(channel, trying, otpMap);
        ACEnterOtp acEnterOtp = new ACEnterOtp();
        acEnterOtp.setMt(otpMaxTry);
        acEnterOtp.setCt(++trying);
        channel.writeAndFlush(acEnterOtp.build(byteBufUtil));
    }

    private void sendArs(Channel channel) {
        ACShowArs acShowArs = new ACShowArs();
        acShowArs.setNum("1234");
        acShowArs.setTimeout(10000);
        channel.writeAndFlush(acShowArs.build(byteBufUtil));
    }

    private void sendPcCert(Channel channel, int trying) {
        if (trying > pcCertMaxTry) {
            pcCertMap.remove(channel);
            loginService.rejectLogin(channel, 0, "Maximum PcCert number of attempts reached");
            return;
        }
        updateMap(channel, trying, pcCertMap);
        ACEnterPcCert acEnterPcCert = new ACEnterPcCert();
        acEnterPcCert.setMt(pcCertMaxTry);
        acEnterPcCert.setCt(++trying);
        channel.writeAndFlush(acEnterPcCert.build(byteBufUtil));
    }

    @Override
    public void challenge(CAChallengeResponse packet, Channel channel) {
        log.info("Password: {}", packet.getPw());    // TODO validation
        ACChallenge2 acChallenge2 = new ACChallenge2();
        acChallenge2.setRound(2);
        acChallenge2.setSalt("1234");
        acChallenge2.setCh(0);
        channel.writeAndFlush(acChallenge2.build(byteBufUtil));
    }

    @Override
    public void challenge(CAChallengeResponse2 packet, Channel channel) {
        if (useOtp) {
            sendOtp(channel, 0);
        } else if (useArs) {
            sendArs(channel);
        } else if (usePcCert) {
            sendPcCert(channel, 0);
        } else {
            // TODO validation
//            loginService.rejectWarnedAccount(channel, 1, "Bad account");
            loginService.allowLogin(channel);
        }
    }

    @Override
    public void processOneTimePassword(CAOtpNumber packet, Channel channel) {
        if (otpMap.containsKey(channel)) {
            if (otpMap.get(channel).getPassword().equals(packet.getNum())) {
                loginService.allowLogin(channel);
            } else {
                sendPcCert(channel, otpMap.get(channel).getCount() + 1);
            }
        } else {
            loginService.rejectLogin(channel, 14, "Invalid packet queue");
        }
    }

    @Override
    public void processPcCertificate(CAPcCertNumber packet, Channel channel) {
        if (pcCertMap.containsKey(channel)) {
            if (pcCertMap.get(channel).getPassword().equals(packet.getNum())) {
                loginService.allowLogin(channel);
            } else {
                sendOtp(channel, pcCertMap.get(channel).getCount() + 1);
            }
        } else {
            loginService.rejectLogin(channel, 14, "Invalid packet queue");
        }
    }
}
