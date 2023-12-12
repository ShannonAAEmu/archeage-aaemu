package com.aaemu.game.service.impl;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.dto.packet.StateLevel;
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
import com.aaemu.game.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Long> accountMap;
    private final ByteBufUtil byteBufUtil;

    public void sendChangeState(int state, Channel channel) {
        ChangeState changeState = new ChangeState();
        changeState.setState(state);
        channel.writeAndFlush(changeState.build(byteBufUtil));
    }

    private void sendGameType(Channel channel) {
        SetGameType setGameType = new SetGameType();
        setGameType.setLevel("loginbg4");
        setGameType.setChecksum(0);
        setGameType.setImmersive(true);
        channel.writeAndFlush(setGameType.build(byteBufUtil));
    }

    private void sendInitialConfig(Channel channel) {
        SCInitialConfig initialConfig = new SCInitialConfig();
        initialConfig.setHost("127.0.0.1:8080");
        initialConfig.setFSet("\u00117");
        initialConfig.setCount(0);
        initialConfig.setSearchLevel(0);
        initialConfig.setBidLevel(0);
        initialConfig.setPostLevel(0);
        channel.writeAndFlush(initialConfig.build(byteBufUtil));
    }

    private void sendAccountInfo(Channel channel) {
        SCAccountInfo accountInfo = new SCAccountInfo();
        accountInfo.setPayMethod(1);
        accountInfo.setPayLocation(1);
        channel.writeAndFlush(accountInfo.build(byteBufUtil));
    }

    private void sendChatSpamDelay(Channel channel) {
        SCChatSpamDelay chatSpamDelay = new SCChatSpamDelay();
        chatSpamDelay.setYellDelay(0);
        chatSpamDelay.setMaxSpamYell(0);
        chatSpamDelay.setSpamYellDelay(0);
        chatSpamDelay.setMaxChatLen(0);
        channel.writeAndFlush(chatSpamDelay.build(byteBufUtil));
    }

    @Override
    public void enterWorld(X2EnterWorld packet, Channel channel) {
        accountMap.replace(channel, packet.getAccountId());
        int cookie = new Random().nextInt(65535);
        X2EnterWorldResponse x2EnterWorldResponse = new X2EnterWorldResponse();
        x2EnterWorldResponse.setReason(0);
        x2EnterWorldResponse.setGm(0);
        x2EnterWorldResponse.setSc(cookie);
        x2EnterWorldResponse.setSp(1250);
        x2EnterWorldResponse.setWf(System.currentTimeMillis());
        channel.writeAndFlush(x2EnterWorldResponse.build(byteBufUtil));
        sendChangeState(0, channel);
    }

    @Override
    public void enterWorld(FinishState packet, Channel channel) {
        if (packet.getState() == StateLevel.ZERO.getState()) {
            sendChangeState(1, channel);
            sendGameType(channel);
            sendInitialConfig(channel);
            sendAccountInfo(channel);
//            sendChatSpamDelay(channel);
        }
    }

    @Override
    public void changeState(long accountId) {
        if (accountMap.containsValue(accountId)) {
            for (Map.Entry<Channel, Long> entry : accountMap.entrySet()) {
                if (entry.getValue() == accountId) {
                    sendChangeState(2, entry.getKey());
                    break;
                }
            }
        }
    }

    @Override
    public void sendListCharacter(CSListCharacter packet, Channel channel) {
        SCCharacterList characterList = new SCCharacterList();
        characterList.setLast(1);
        characterList.setCount(0);
//        characterList.setCharacterList(null);
        channel.writeAndFlush(characterList.build(byteBufUtil));
    }
}
