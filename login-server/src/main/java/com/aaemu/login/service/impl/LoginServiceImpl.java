package com.aaemu.login.service.impl;

import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.dto.packet.server.ACAccountWarned;
import com.aaemu.login.service.dto.packet.server.ACAuthResponse;
import com.aaemu.login.service.dto.packet.server.ACJoinResponse;
import com.aaemu.login.service.dto.packet.server.ACLoginDenied;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtils byteBufUtils;

    @Override
    public void rejectLogin(Channel channel, int reason, String msg) {
        ACLoginDenied acLoginDenied = new ACLoginDenied();
        acLoginDenied.setReason(reason);
        acLoginDenied.setVp("");
        acLoginDenied.setMsg(msg);
        channel.writeAndFlush(acLoginDenied.build(byteBufUtils));
    }

    @Override
    public void rejectWarnedAccount(Channel channel, int source, String msg) {
        ACAccountWarned acAccountWarned = new ACAccountWarned();
        acAccountWarned.setSource(source);
        acAccountWarned.setMsg(msg);
        channel.writeAndFlush(acAccountWarned.build(byteBufUtils));
    }

    @Override
    public boolean isValidAccount(Channel channel) {
        if (!accountMap.containsKey(channel)) {
            return false;
        }
        accountMap.get(channel).setId(1);
        log.info("Validate: {}", accountMap.get(channel));
        return true;    // TODO validation
    }

    @Override
    public void allowLogin(Channel channel) {
        ACJoinResponse acJoinResponse = new ACJoinResponse();
        acJoinResponse.setReason(0);
        acJoinResponse.setAfs(10000000); // 1442306
        channel.writeAndFlush(acJoinResponse.build(byteBufUtils));
        ACAuthResponse acAuthResponse = new ACAuthResponse();
        acAuthResponse.setAccountId((int) accountMap.get(channel).getId());
        acAuthResponse.setWsk("");
        channel.writeAndFlush(acAuthResponse.build(byteBufUtils));
    }
}
