package com.aaemu.login.service.impl;

import com.aaemu.login.client.GameServer;
import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.LoginService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.client.AddressDto;
import com.aaemu.login.service.dto.client.LoginAccountDto;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACEnterWorldDenied;
import com.aaemu.login.service.dto.packet.server.ACWorldCookie;
import com.aaemu.login.service.dto.packet.server.ACWorldList;
import com.aaemu.login.service.dto.packet.server.ACWorldQueue;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final Map<Channel, Integer> cookieMap;
    private final Map<Channel, Account> accountMap;
    private final LoginService loginService;
    private final ByteBufUtil byteBufUtil;
    private final AuthService authService;
    private final GameServer gameServer;

    private boolean isValidListWorldFlag(CAListWorld listWorld) {
        return listWorld.getFlag() == 0;   // TODO check
    }

    private boolean isValidWorldFlag(CAEnterWorld enterWorld) {
        return enterWorld.getFlag() == 0;   // TODO check
    }

    private void sendWorldList(Channel channel) {
        ACWorldList acWorldList = new ACWorldList();
        acWorldList.setServerDtoList(gameServer.getServerList());
        acWorldList.setCount(acWorldList.getServerDtoList().size());
        LoginAccountDto loginAccountDto = new LoginAccountDto();
        loginAccountDto.setName(accountMap.get(channel).getName());
        acWorldList.setCharacterDtoList(gameServer.getCharacterList(loginAccountDto));
        acWorldList.setChCount(acWorldList.getCharacterDtoList().size());
        channel.writeAndFlush(acWorldList.build(byteBufUtil));
    }

    @Override
    public void requestList(CAListWorld packet, Channel channel) {
        if (isValidListWorldFlag(packet)) {
            sendWorldList(channel);
        } else {
            cookieMap.remove(channel);
            loginService.rejectLogin(channel, 14, "Invalid world list flag");
        }
    }

    @Override
    public void enterWorld(CAEnterWorld packet, Channel channel) {
        if (isValidWorldFlag(packet)) {
            int worldId = packet.getWid();
            if (gameServer.hasQueue(worldId)) {
                LoginAccountDto loginAccountDto = new LoginAccountDto();
                loginAccountDto.setName(accountMap.get(channel).getName());
                QueueStatusDto queueStatusDto = gameServer.getQueueStatus(worldId, loginAccountDto);
                ACWorldQueue acWorldQueue = new ACWorldQueue();
                acWorldQueue.setWid(worldId);
                acWorldQueue.setTurnCount(queueStatusDto.getTurnCount());
                acWorldQueue.setTotalCount(queueStatusDto.getTotalCount());
                channel.writeAndFlush(acWorldQueue.build(byteBufUtil));
            } else {
                int cookie = new Random().nextInt(65535);
                AddressDto addressDto = gameServer.getAddress();
                ACWorldCookie acWorldCookie = new ACWorldCookie();
                acWorldCookie.setCookie(cookie);
                acWorldCookie.setIp(addressDto.getIp());
                acWorldCookie.setPort(addressDto.getPort());
                cookieMap.put(channel, cookie);
                channel.writeAndFlush(acWorldCookie.build(byteBufUtil));
            }
        } else {
            cookieMap.remove(channel);
            ACEnterWorldDenied acEnterWorldDenied = new ACEnterWorldDenied();
            acEnterWorldDenied.setReason(14);
            channel.writeAndFlush(acEnterWorldDenied.build(byteBufUtil));
        }
    }

    @Override
    public void cancelEnterWorld(CACancelEnterWorld packet, Channel channel) {
        cookieMap.remove(channel);
        sendWorldList(channel);
    }

    @Override
    public void requestReconnect(CARequestReconnect packet, Channel channel) {
        if (cookieMap.containsKey(channel) && cookieMap.get(channel).longValue() == packet.getCookie()) {
            authService.requestReconnect(packet, channel);
        } else {
            cookieMap.remove(channel);
            loginService.rejectWarnedAccount(channel, 14, "Invalid cookie");
        }
    }
}
