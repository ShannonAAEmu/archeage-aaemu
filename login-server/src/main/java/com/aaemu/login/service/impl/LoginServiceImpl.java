package com.aaemu.login.service.impl;

import com.aaemu.login.data.entity.AccountEntity;
import com.aaemu.login.service.GameServerService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.Repository;
import com.aaemu.login.service.dto.packet.server.ACAccountWarned;
import com.aaemu.login.service.dto.packet.server.ACAuthResponse;
import com.aaemu.login.service.dto.packet.server.ACJoinResponse;
import com.aaemu.login.service.dto.packet.server.ACLoginDenied;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class LoginServiceImpl implements LoginService {
    private final GameServerService gameServerService;
    private final Map<Channel, Account> accountMap;
    private final Repository repository;
    private final ByteBufUtil byteBufUtil;

    @Override
    public boolean isPresentAccount(Account account) {
        if (Objects.isNull(account)) {
            return false;
        }
        Optional<AccountEntity> entityOptional = repository.getAccount(account);
        if (entityOptional.isPresent()) {
            AccountEntity accountEntity = entityOptional.get();
            account.setId(accountEntity.getId());
            account.setCookie(account.getId());
            account.setDev(accountEntity.isDev());
            account.setActive(accountEntity.isActive());
            account.setPassword(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean isActiveAccount(Account account) {
        return account.isActive();
    }

    @Override
    public void rejectLogin(Channel channel, int reason, String msg) {
        ACLoginDenied acLoginDenied = new ACLoginDenied();
        acLoginDenied.setReason(reason);
        acLoginDenied.setVp("");
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

    @Override
    public void allowLogin(Channel channel) {
        ACJoinResponse acJoinResponse = new ACJoinResponse();
        acJoinResponse.setReason((short) 0);
        acJoinResponse.setAccountFutureSet(gameServerService.getAccountFutureSet());
        channel.writeAndFlush(acJoinResponse.build(byteBufUtil));
        ACAuthResponse acAuthResponse = new ACAuthResponse();
        acAuthResponse.setAccountId(accountMap.get(channel).getId());
        acAuthResponse.setWsk("");
        channel.writeAndFlush(acAuthResponse.build(byteBufUtil));
    }
}
