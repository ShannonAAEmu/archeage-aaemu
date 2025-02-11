package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACChallenge;
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
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtils byteBufUtils;

    @Override
    public void requestAuth(CARequestAuth packet) {
        log.info("Request auth from account: {}", packet.getAccount());
        accountMap.put(packet.getChannel(), new Account(packet.getAccount()));
        sendChallenge(packet.getChannel());
    }

    @Override
    public void requestReconnect(CARequestReconnect packet) {
        sendChallenge(packet.getChannel());
    }

    private void sendChallenge(Channel channel) {
        ACChallenge acChallenge = new ACChallenge();
        acChallenge.setSalt(0);
        acChallenge.setCh(0);
        channel.writeAndFlush(acChallenge.build(byteBufUtils));
    }
}
