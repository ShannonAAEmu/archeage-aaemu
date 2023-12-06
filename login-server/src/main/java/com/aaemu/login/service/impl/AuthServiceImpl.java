package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.annotation.TestData;
import com.aaemu.login.service.dto.CharacterDto;
import com.aaemu.login.service.dto.ServerDto;
import com.aaemu.login.service.entity.OneTimePassword;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.entity.packet.client.CAListWorld;
import com.aaemu.login.service.entity.packet.client.CAOtpNumber;
import com.aaemu.login.service.entity.packet.client.CARequestAuth;
import com.aaemu.login.service.entity.packet.server.ACAuthResponse;
import com.aaemu.login.service.entity.packet.server.ACChallenge;
import com.aaemu.login.service.entity.packet.server.ACChallenge2;
import com.aaemu.login.service.entity.packet.server.ACEnterOtp;
import com.aaemu.login.service.entity.packet.server.ACJoinResponse;
import com.aaemu.login.service.entity.packet.server.ACLoginDenied;
import com.aaemu.login.service.entity.packet.server.ACShowArs;
import com.aaemu.login.service.entity.packet.server.ACWorldList;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ByteBufUtil byteBufUtil;

    private final Map<Channel, OneTimePassword> otpMap;

    @Value("${auth.otp}")
    private boolean useOtp;

    @Value("${auth.otp_max_try}")
    private int otpMaxTry;

    @Value("${auth.ars}")
    private boolean useArs;

    public AuthServiceImpl(ByteBufUtil byteBufUtil) {
        this.byteBufUtil = byteBufUtil;
        this.otpMap = new ConcurrentHashMap<>();
    }

    private void sendLoginDenied(Channel channel, String msg) {
        ACLoginDenied acLoginDenied = new ACLoginDenied();
        acLoginDenied.setReason(0);
        acLoginDenied.setVp(null);
        acLoginDenied.setMsg(msg);
        channel.writeAndFlush(acLoginDenied.build(byteBufUtil));
    }

    private void sendLoginAllowed(Channel channel) {
        ACJoinResponse acJoinResponse = new ACJoinResponse();
        acJoinResponse.setReason(0);
        acJoinResponse.setAfs(1442306);
        channel.writeAndFlush(acJoinResponse.build(byteBufUtil));
        ACAuthResponse acAuthResponse = new ACAuthResponse();
        acAuthResponse.setAccountId(1);
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 16) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        acAuthResponse.setWsk(sb.toString());
        channel.writeAndFlush(acAuthResponse.build(byteBufUtil));
    }

    private void sendOtp(Channel channel, int trying) {
        if (trying > otpMaxTry) {
            otpMap.remove(channel);
            sendLoginDenied(channel, "Maximum number of attempts reached");
            return;
        }
        if (otpMap.containsKey(channel)) {
            otpMap.get(channel).setCount(trying);
        } else {
            otpMap.put(channel, new OneTimePassword(trying, "12345678"));
        }
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

    @Override
    public void requestAuth(CARequestAuth requestAuth, Channel channel) {
        log.info("Auth user: {}", requestAuth.getAccount());
        ACChallenge acChallenge = new ACChallenge();
        acChallenge.setSalt(0);
        acChallenge.setCh(0);
        channel.writeAndFlush(acChallenge.build(byteBufUtil));
    }

    @Override
    public void firstChallengeResponse(CAChallengeResponse challengeResponse, Channel channel) {
        log.info("Password: {}", challengeResponse.getPw());
        ACChallenge2 acChallenge2 = new ACChallenge2();
        acChallenge2.setRound(2);
        acChallenge2.setSalt("1234");
        acChallenge2.setCh(0);
        channel.writeAndFlush(acChallenge2.build(byteBufUtil));
    }

    @Override
    public void secondChallengeResponse(CAChallengeResponse2 challengeResponse2, Channel channel) {
        if (useOtp) {
            sendOtp(channel, 0);
        } else if (useArs) {
            sendArs(channel);
        } else {
            // TODO validation
            sendLoginAllowed(channel);
        }
    }

    @Override
    public void otpProcess(CAOtpNumber caOtpNumber, Channel channel) {
        if (otpMap.containsKey(channel)) {
            if (otpMap.get(channel).getPassword().equals(caOtpNumber.getNum())) {
                sendLoginAllowed(channel);
            } else {
                sendOtp(channel, otpMap.get(channel).getCount() + 1);
            }
        } else {
            sendLoginDenied(channel, "Invalid packet queue");
        }
    }

    @Override
    public void listWorldProcess(CAListWorld listWorld, Channel channel) {
        if (listWorld.isValidFlag()) {
            ACWorldList acWorldList = new ACWorldList();
            List<ServerDto> serverDtoList = new ArrayList<>();
            serverDtoList.add(getTempServer());
            acWorldList.setServerDtoList(serverDtoList);
            acWorldList.setCount(acWorldList.getServerDtoList().size());
            List<CharacterDto> characterDtoList = new ArrayList<>();
//            characterDtoList.add(getTempCharacter());
            acWorldList.setCharacterDtoList(characterDtoList);
            acWorldList.setChCount(acWorldList.getCharacterDtoList().size());
            channel.writeAndFlush(acWorldList.build(byteBufUtil));
        }
    }

    @TestData
    private ServerDto getTempServer() {
        ServerDto serverDto = new ServerDto();
        serverDto.setId(1);
        serverDto.setName("AAEMU");
        serverDto.setAvailable(true);
        if (serverDto.isAvailable()) {
            serverDto.setCon(0);
            List<Boolean> rCon = new ArrayList<>();
            rCon.add(false); // warborn
            rCon.add(false); // nuian
            rCon.add(false); // returned
            rCon.add(false); // fairy
            rCon.add(false); // elf
            rCon.add(false); // hariharan
            rCon.add(false); // ferre
            rCon.add(false); // dwarf
            rCon.add(false); // none
            serverDto.setRCon(rCon);
        }
        return serverDto;
    }

    @TestData
    private CharacterDto getTempCharacter() {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setAccountId(1);
        characterDto.setWorldId(1);
        characterDto.setCharId(1);
        characterDto.setName("Shannon");
        characterDto.setCharRace(1);
        characterDto.setCharGender(1);
        characterDto.setGuid("1234567890123456");
        characterDto.setV(0);
        return characterDto;
    }
}
