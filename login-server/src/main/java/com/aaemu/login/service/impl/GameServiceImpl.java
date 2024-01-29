package com.aaemu.login.service.impl;

import com.aaemu.login.client.GameServer;
import com.aaemu.login.service.GameService;
import com.aaemu.login.service.dto.client.AddressDto;
import com.aaemu.login.service.dto.client.QueueAccountDto;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.server.ACWorldCookie;
import com.aaemu.login.service.dto.packet.server.ACWorldList;
import com.aaemu.login.service.dto.packet.server.ACWorldQueue;
import com.aaemu.login.service.model.AuthAccount;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final Map<Channel, AuthAccount> accountMap;
    private final ByteBufUtil byteBufUtil;
    private final GameServer gameServer;

    private void sendWorldList(Channel channel) {
        ACWorldList acWorldList = new ACWorldList();
        acWorldList.setServerDtoList(gameServer.getServerList());
        acWorldList.setCount(acWorldList.getServerDtoList().size());
        QueueAccountDto queueAccountDto = new QueueAccountDto();
        queueAccountDto.setId(accountMap.get(channel).getId());
        acWorldList.setCharacterDtoList(gameServer.getCharacterList(queueAccountDto));
        acWorldList.setChCount(acWorldList.getCharacterDtoList().size());
        channel.writeAndFlush(acWorldList.build(byteBufUtil));
    }

    @Override
    public void requestList(CAListWorld packet, Channel channel) {
        sendWorldList(channel);
    }

    @Override
    public void enterWorld(CAEnterWorld packet, Channel channel) {
        int worldId = packet.getWid();
        if (gameServer.hasQueue(worldId)) {
            QueueAccountDto queueAccountDto = new QueueAccountDto();
            queueAccountDto.setId(accountMap.get(channel).getId());
            QueueStatusDto queueStatusDto = gameServer.getQueueStatus(worldId, queueAccountDto);
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
            channel.writeAndFlush(acWorldCookie.build(byteBufUtil));
        }
    }

    @Override
    public void cancelEnterWorld(CACancelEnterWorld packet, Channel channel) {
        sendWorldList(channel);
    }
}
