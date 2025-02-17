package com.aaemu.game.service.impl;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.dto.client.AccountFutureSet;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.ChangeState;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.SetGameType;
import com.aaemu.game.service.dto.packet.server.SCAccountInfo;
import com.aaemu.game.service.dto.packet.server.SCCharacterList;
import com.aaemu.game.service.dto.packet.server.SCChatSpamDelay;
import com.aaemu.game.service.dto.packet.server.SCInitialConfig;
import com.aaemu.game.service.dto.packet.server.X2EnterWorldResponse;
import com.aaemu.game.service.enums.pay.PayLocation;
import com.aaemu.game.service.enums.pay.PayMethod;
import com.aaemu.game.service.enums.packet.StateLevel;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.model.FutureSet;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtil byteBufUtil;

    @Value("${game_server.config.total_characters_limit}")
    private byte totalCharactersLimit;

    @Value("${game_server.config.is_pre_select_character_period}")
    private boolean isPreSelectCharacterPeriod;

    @Value("${game_server.stream_server.port}")
    private int streamServerPort;

    @Override
    public AccountFutureSet getAccountFutureSet() {
        return new AccountFutureSet(totalCharactersLimit, isPreSelectCharacterPeriod);
    }

    @Override
    public void enterWorld(X2EnterWorld packet) {
        Account account = new Account(packet.getAccountId(), packet.getCookie());
        account.setZoneId(packet.getZoneId());
        accountMap.put(packet.getChannel(), account);
        X2EnterWorldResponse x2EnterWorldResponse = new X2EnterWorldResponse();
        x2EnterWorldResponse.setReason(0);
        x2EnterWorldResponse.setGameMode(0);
        x2EnterWorldResponse.setStreamServerCookie(account.getCookie());
        x2EnterWorldResponse.setStreamServerPort(streamServerPort);
        x2EnterWorldResponse.setWf(System.currentTimeMillis());
        packet.getChannel().writeAndFlush(x2EnterWorldResponse.build(byteBufUtil));
        sendChangeState(0, packet.getChannel());
    }

    @Override
    public void finishState(FinishState packet) {
        if (packet.getState() == StateLevel._0.getState()) {
            sendChangeState(1, packet.getChannel());
        }
        // TODO Error send
    }

    @Override
    public void changeState(int accountId) {
        for (Map.Entry<Channel, Account> entry : accountMap.entrySet()) {
            if (entry.getValue().getId() == accountId) {
                sendGameType(entry.getKey());
                sendInitialConfig(entry.getKey());
                // TODO fix
                sendAccountInfo(entry.getKey());
                sendChatSpamDelay(entry.getKey());
                //
                sendChangeState(2, entry.getKey());
                break;
            }
        }
    }

    @Override
    public void sendCharacterList(CSListCharacter packet) {
        SCCharacterList characterList = new SCCharacterList();
        characterList.setLast(true);    // TODO check
        characterList.setCharacterList(new ArrayList<>());  // TODO set list
        packet.getChannel().writeAndFlush(characterList.build(byteBufUtil));
    }

    private void sendChangeState(int state, Channel channel) {
        channel.writeAndFlush(new ChangeState(state).build(byteBufUtil));
    }

    private void sendGameType(Channel channel) {
        SetGameType setGameType = new SetGameType();
        setGameType.setLevel("loginbg4");   // TODO get info
        setGameType.setChecksum(0);
        setGameType.setImmersive(true);
        channel.writeAndFlush(setGameType.build(byteBufUtil));
    }

    private void sendInitialConfig(Channel channel) {
        SCInitialConfig initialConfig = new SCInitialConfig();
        initialConfig.setHost("127.0.0.1:8080");    // TODO get info
        initialConfig.setFutureSet(new FutureSet());    // TODO get info
        initialConfig.setCount(0);
        initialConfig.setSearchLevel(0);    // TODO get info
        initialConfig.setBidLevel(0);   // TODO get info
        initialConfig.setPostLevel(0);  // TODO get info
        channel.writeAndFlush(initialConfig.build(byteBufUtil));
    }

    private void sendAccountInfo(Channel channel) {
        SCAccountInfo accountInfo = new SCAccountInfo();
        accountInfo.setPayMethod(PayMethod.PERSON); // TODO get info
        accountInfo.setPayLocation(PayLocation.PERSON); // TODO get info
        channel.writeAndFlush(accountInfo.build(byteBufUtil));
    }

    private void sendChatSpamDelay(Channel channel) {
        SCChatSpamDelay chatSpamDelay = new SCChatSpamDelay();
        chatSpamDelay.setYellDelay(0);  // TODO get info
        chatSpamDelay.setMaxSpamYell(0);    // TODO get info
        chatSpamDelay.setSpamYellDelay(0);  // TODO get info
        chatSpamDelay.setMaxChatLen(0); // TODO get info
        channel.writeAndFlush(chatSpamDelay.build(byteBufUtil));
    }
}
