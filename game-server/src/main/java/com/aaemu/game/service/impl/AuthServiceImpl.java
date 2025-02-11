package com.aaemu.game.service.impl;

import com.aaemu.game.service.AuthService;
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
import com.aaemu.game.service.enums.StateLevel;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtils byteBufUtils;

    @Value("${game_server.stream_server.port}")
    private int streamServerPort;

    @Override
    public void enterWorld(X2EnterWorld packet) {
        accountMap.put(packet.getChannel(), new Account(packet.getAccountId()));
        int cookie = new Random().nextInt(65535);
        X2EnterWorldResponse x2EnterWorldResponse = new X2EnterWorldResponse();
        x2EnterWorldResponse.setReason(0);
        x2EnterWorldResponse.setGm(0);
        x2EnterWorldResponse.setSc(cookie);
        x2EnterWorldResponse.setSp(streamServerPort);
        x2EnterWorldResponse.setWf(System.currentTimeMillis());
        packet.getChannel().writeAndFlush(x2EnterWorldResponse.build(byteBufUtils));
        sendChangeState(0, packet.getChannel());
    }

    @Override
    public void finishState(FinishState packet) {
        if (packet.getState() == StateLevel._0.getState()) {
            sendChangeState(1, packet.getChannel());
        }
    }

    @Override
    public void changeState(long accountId) {
        for (Map.Entry<Channel, Account> entry : accountMap.entrySet()) {
            if (entry.getValue().getId() == accountId) {
                sendGameType(entry.getKey());
                sendInitialConfig(entry.getKey());
                sendAccountInfo(entry.getKey());
                sendChatSpamDelay(entry.getKey());
                sendChangeState(2, entry.getKey());
                break;
            }
        }
    }

    @Override
    public void listCharacter(CSListCharacter packet) {
        SCCharacterList characterList = new SCCharacterList();
        characterList.setCharacterList(new ArrayList<>());
        packet.getChannel().writeAndFlush(characterList.build(byteBufUtils));
    }

    private void sendChangeState(int state, Channel channel) {
        ChangeState changeState = new ChangeState();
        changeState.setState(state);
        channel.writeAndFlush(changeState.build(byteBufUtils));
    }

    private void sendGameType(Channel channel) {
        SetGameType setGameType = new SetGameType();
        setGameType.setLevel("loginbg4");
        setGameType.setChecksum(0);
        setGameType.setImmersive(true);
        channel.writeAndFlush(setGameType.build(byteBufUtils));
    }

    private void sendInitialConfig(Channel channel) {
        SCInitialConfig initialConfig = new SCInitialConfig();
        initialConfig.setHost("127.0.0.1:8080");
        initialConfig.setFSet("0"); // siege
        initialConfig.setCount(0);  // unknown
        initialConfig.setSearchLevel(0);
        initialConfig.setBidLevel(0);
        initialConfig.setPostLevel(0);
        channel.writeAndFlush(initialConfig.build(byteBufUtils));
    }

    private void sendAccountInfo(Channel channel) {
        SCAccountInfo accountInfo = new SCAccountInfo();
        accountInfo.setPayMethod(1);
        accountInfo.setPayLocation(1);
        channel.writeAndFlush(accountInfo.build(byteBufUtils));
    }

    private void sendChatSpamDelay(Channel channel) {
        SCChatSpamDelay chatSpamDelay = new SCChatSpamDelay();
        chatSpamDelay.setYellDelay(0);
        chatSpamDelay.setMaxSpamYell(0);
        chatSpamDelay.setSpamYellDelay(0);
        chatSpamDelay.setMaxChatLen(0);
        channel.writeAndFlush(chatSpamDelay.build(byteBufUtils));
    }
}
