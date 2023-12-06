package com.aaemu.login.service.impl;

import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.dto.packet.server.ACAccountWarned;
import com.aaemu.login.service.dto.packet.server.ACAuthResponse;
import com.aaemu.login.service.dto.packet.server.ACJoinResponse;
import com.aaemu.login.service.dto.packet.server.ACLoginDenied;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final ByteBufUtil byteBufUtil;

    @Override
    public void allowLogin(Channel channel) {
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

    @Override
    public void rejectLogin(Channel channel, int reason, String msg) {
        ACLoginDenied acLoginDenied = new ACLoginDenied();
        acLoginDenied.setReason(reason);
        acLoginDenied.setVp(null);
        acLoginDenied.setMsg(msg);
        channel.writeAndFlush(acLoginDenied.build(byteBufUtil));
    }

    @Override
    public void rejectWarnedAccount(Channel channel, int source, String msg) {
        ACAccountWarned acAccountWarned = new ACAccountWarned();
        acAccountWarned.setSource(source);
        acAccountWarned.setMsg(msg);
        channel.writeAndFlush(acAccountWarned.build(byteBufUtil));
    }
}
