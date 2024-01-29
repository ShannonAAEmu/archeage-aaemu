package com.aaemu.game.service.impl;

import com.aaemu.game.client.StreamServer;
import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.dto.client.AddressDto;
import com.aaemu.game.service.dto.client.StreamAccountDto;
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
import com.aaemu.game.service.exception.GameServerException;
import com.aaemu.game.service.model.GameAccount;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, GameAccount> accountMap;
    private final StreamServer streamServer;
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
        try {
            SCInitialConfig initialConfig = new SCInitialConfig();
            initialConfig.setHost(InetAddress.getLocalHost().getHostAddress().concat(":8080"));
            initialConfig.setFSet("\u00117");
            initialConfig.setCount(0);
            initialConfig.setSearchLevel(0);
            initialConfig.setBidLevel(0);
            initialConfig.setPostLevel(0);
            channel.writeAndFlush(initialConfig.build(byteBufUtil));
        } catch (UnknownHostException e) {
            throw new GameServerException(e);
        }
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
    public void firstStepEnterWorld(X2EnterWorld packet, Channel channel) {
        AddressDto streamAddress = streamServer.getStreamAddress();
        accountMap.replace(channel, new GameAccount(packet.getAccountId()));
        X2EnterWorldResponse x2EnterWorldResponse = new X2EnterWorldResponse();
        x2EnterWorldResponse.setReason(0);
        x2EnterWorldResponse.setGm(0);
        x2EnterWorldResponse.setSc(new Random().nextInt(65535));
        x2EnterWorldResponse.setSp(streamAddress.getPort());
        x2EnterWorldResponse.setWf(System.currentTimeMillis());
        channel.writeAndFlush(x2EnterWorldResponse.build(byteBufUtil));
        sendChangeState(0, channel);
    }

    @Override
    public void secondStepEnterWorld(FinishState packet, Channel channel) {
        if (packet.getState() == StateLevel.ZERO.getState()) {
            sendChangeState(1, channel);
            sendGameType(channel);
            sendInitialConfig(channel);
            sendAccountInfo(channel);
            sendChatSpamDelay(channel);
        }
    }

    @Override
    public void changeState(StreamAccountDto streamAccountDto) {
        for (Map.Entry<Channel, GameAccount> entry : accountMap.entrySet()) {
            if (entry.getValue().getId().equals(streamAccountDto.getId())) {
                entry.getValue().setStreamCookie(streamAccountDto.getCookie());
                sendChangeState(2, entry.getKey());
                break;
            }
        }
    }

    @Override
    public void sendCharacterList(CSListCharacter packet, Channel channel) {
        SCCharacterList characterList = new SCCharacterList();
        characterList.setCharacterList(new ArrayList<>());
        channel.writeAndFlush(characterList.build(byteBufUtil));
    }
}
