package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.GameServerService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACEnterWorldDenied;
import com.aaemu.login.service.dto.packet.server.ACWorldCookie;
import com.aaemu.login.service.dto.packet.server.ACWorldList;
import com.aaemu.login.service.dto.packet.server.ACWorldQueue;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final GameServerService gameServerService;
    private final Map<Channel, Account> accountMap;
    private final Map<Channel, Integer> queueMap;
    private final ByteBufUtils byteBufUtils;
    private final AuthService authService;

    @Value("${login_server.game_server.id}")
    private int gameServerId;

    @Override
    public void requestList(Channel channel) {
        ServerDto serverInfo = gameServerService.getServerInfo(accountMap.get(channel));
        ACWorldList acWorldList = new ACWorldList();
        acWorldList.setServerDto(serverInfo);
        acWorldList.setCount(Objects.isNull(serverInfo) ? 0 : 1);
        acWorldList.setCharacterDtoList(serverInfo.getCharacters());
        acWorldList.setChCount(acWorldList.getCharacterDtoList().size());
        channel.writeAndFlush(acWorldList.build(byteBufUtils));
    }

    @Override
    public void requestEnter(Channel channel, int worldId) {
        if (gameServerId != worldId) {
            ACEnterWorldDenied acEnterWorldDenied = new ACEnterWorldDenied();
            acEnterWorldDenied.setReason(28);
            channel.writeAndFlush(acEnterWorldDenied.build(byteBufUtils));
            return;
        }
        QueueStatusDto queueStatus = gameServerService.getQueueStatus(worldId, accountMap.get(channel));
        if (queueStatus.getTurnCount() == 0) {
            queueMap.remove(channel);
            int cookie = new Random().nextInt(65535);
            channel.writeAndFlush(new ACWorldCookie(cookie, queueStatus.getIp(), queueStatus.getPort()).build(byteBufUtils));
        } else {
            queueMap.put(channel, queueStatus.getTurnCount());
            ACWorldQueue acWorldQueue = new ACWorldQueue();
            acWorldQueue.setWid(worldId);
            acWorldQueue.setTurnCount(queueStatus.getTurnCount());
            acWorldQueue.setTotalCount(queueStatus.getTotalCount());
            channel.writeAndFlush(acWorldQueue.build(byteBufUtils));
        }
    }

    @Override
    public void cancelEnterWorld(CACancelEnterWorld packet) {
        queueMap.remove(packet.getChannel());
        requestList(packet.getChannel());
    }

    @Override
    public void requestReconnect(CARequestReconnect packet) {
        authService.requestReconnect(packet);
    }

    @Scheduled(fixedRateString = "${login_server.game_server.queue_time}")
    private void cleanQueueMap() {
        queueMap.forEach((channel, turnCount) -> {
            if (channel.isActive()) {
                requestEnter(channel, gameServerId);
            }
        });
    }
}
