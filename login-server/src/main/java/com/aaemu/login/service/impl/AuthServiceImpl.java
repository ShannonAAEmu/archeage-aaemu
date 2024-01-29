package com.aaemu.login.service.impl;

import com.aaemu.login.data.AccountRepository;
import com.aaemu.login.data.entity.Account;
import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACChallenge;
import com.aaemu.login.service.mapper.EntityDtoMapper;
import com.aaemu.login.service.model.AuthAccount;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, AuthAccount> accountMap;
    private final AccountRepository accountRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final LoginService loginService;
    private final ByteBufUtil byteBufUtil;

    @Value("${auth.auto_registration}")
    private boolean isAutoRegistration;

    private void sendChallenge(Channel channel) {
        ACChallenge acChallenge = new ACChallenge();
        acChallenge.setSalt(0);
        acChallenge.setCh(0);
        channel.writeAndFlush(acChallenge.build(byteBufUtil));
    }

    @Override
    public void requestAuth(CARequestAuth packet, Channel channel) {
        Optional<Account> accountOptional = accountRepository.findAccountByName(packet.getAccount());
        if (accountOptional.isEmpty()) {
            if (isAutoRegistration) {
                AuthAccount authAccount = new AuthAccount();
                authAccount.setName(packet.getAccount());
                accountMap.replace(channel, authAccount);
                sendChallenge(channel);
            } else {
                loginService.rejectLogin(channel, 0, "Account not found");
            }
        } else {
            accountMap.replace(channel, entityDtoMapper.toDto(accountOptional.get()));
            sendChallenge(channel);
        }
    }

    @Override
    public void requestReconnect(CARequestReconnect packet, Channel channel) {
        Optional<Account> accountOptional = accountRepository.findById(packet.getAid());
        if (accountOptional.isEmpty()) {
            loginService.rejectLogin(channel, 0, "Account not found");
        } else {
            accountMap.replace(channel, entityDtoMapper.toDto(accountOptional.get()));
            sendChallenge(channel);
        }
    }
}
