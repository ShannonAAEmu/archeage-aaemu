package com.aaemu.login.service.impl;

import com.aaemu.login.service.GameServerService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.client.QueueStatus;
import com.aaemu.login.service.dto.client.ServerInfo;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.server.ACEnterWorldDenied;
import com.aaemu.login.service.dto.packet.server.ACWorldCookie;
import com.aaemu.login.service.dto.packet.server.ACWorldList;
import com.aaemu.login.service.dto.packet.server.ACWorldQueue;
import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WorldServiceImpl implements WorldService {
    private final GameServerService gameServerService;
    private final Map<Channel, Account> accountMap;
    private final Map<Channel, Short> queueMap;
    private final ByteBufUtil byteBufUtil;

    @Value("${login_server.game_server.id}")
    private byte gameServerId;

    @Override
    public void sendWorldList(Channel channel) {
        ServerInfo serverInfo = gameServerService.getServerInfo(accountMap.get(channel));
        channel.writeAndFlush(new ACWorldList(serverInfo).build(byteBufUtil));
    }

    @Override
    public void enterToWorld(Channel channel, byte worldId) {
        if (gameServerId != worldId) {
            ACEnterWorldDenied acEnterWorldDenied = new ACEnterWorldDenied();
            acEnterWorldDenied.setReason(28);
            channel.writeAndFlush(acEnterWorldDenied.build(byteBufUtil));
            return;
        }
        QueueStatus queueStatus = gameServerService.getQueueStatus(accountMap.get(channel));
        if (queueStatus.getTurnCount() == 0) {
            queueMap.remove(channel);
            channel.writeAndFlush(new ACWorldCookie(accountMap.get(channel).getCookie(), queueStatus).build(byteBufUtil));
        } else {
            queueMap.put(channel, queueStatus.getTurnCount());
            ACWorldQueue acWorldQueue = new ACWorldQueue();
            acWorldQueue.setWorldId(worldId);
            acWorldQueue.setTurnCount(queueStatus.getTurnCount());
            acWorldQueue.setTotalCount(queueStatus.getTotalCount());
            channel.writeAndFlush(acWorldQueue.build(byteBufUtil));
        }
    }

    @Override
    public void cancelEnterWorld(CACancelEnterWorld packet) {
        queueMap.remove(packet.getChannel());
        sendWorldList(packet.getChannel());
    }

    @Scheduled(fixedRateString = "${login_server.game_server.queue_time}")
    private void cleanQueueMap() {
        queueMap.forEach((channel, turnCount) -> {
            if (channel.isActive()) {
                enterToWorld(channel, gameServerId);
            }
        });
    }
}
