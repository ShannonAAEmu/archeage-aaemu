package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.model.Account;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {
    private static final String RECONNECT = "Request reconnect from account id: {}cookie: {}, world id: {}";
    private final ChallengeService challengeService;
    private final Map<Channel, Account> accountMap;

    @Override
    public void auth(CARequestAuth packet) {
        Account account = new Account(packet.getChannel());
        account.setName(packet.getAccount());
        account.setDev(packet.isDev());
        accountMap.put(packet.getChannel(), account);
        challengeService.send(packet.getChannel());
    }

    @Override
    public void requestReconnect(CARequestReconnect packet) {
        log.info(RECONNECT, packet.getAccountId(), packet.getCookie(), packet.getWorldId());
        Account account = new Account(packet.getChannel());
        account.setId(packet.getAccountId());
        account.setCookie(packet.getCookie());
        accountMap.put(packet.getChannel(), account);
        challengeService.send(packet.getChannel());
    }
}
