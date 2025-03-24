package com.aaemu.zone.service.impl;

import com.aaemu.zone.service.JoinService;
import com.aaemu.zone.service.dto.packet.client.ZWJoinPacket;
import com.aaemu.zone.service.dto.packet.server.WZJoinResponse;
import com.aaemu.zone.service.dto.packet.server.WZSpawnerList;
import com.aaemu.zone.service.model.Account;
import com.aaemu.zone.service.model.FutureSet;
import com.aaemu.zone.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtil byteBufUtil;

    @Override
    public void join(ZWJoinPacket packet) {
        accountMap.put(packet.getChannel(), new Account(packet.getAccountId()));
        WZJoinResponse joinResponse = new WZJoinResponse();
        joinResponse.setReason(0);
        joinResponse.setFutureSet(new FutureSet());
        joinResponse.setBf(false);
        packet.getChannel().writeAndFlush(joinResponse.build(byteBufUtil));
        WZSpawnerList spawnerList = new WZSpawnerList();
        spawnerList.setLast(true);
        spawnerList.setCount((byte) 0);
        packet.getChannel().writeAndFlush(spawnerList.build(byteBufUtil));
    }
}
