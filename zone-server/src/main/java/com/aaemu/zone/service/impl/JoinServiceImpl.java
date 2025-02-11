package com.aaemu.zone.service.impl;

import com.aaemu.zone.service.JoinService;
import com.aaemu.zone.service.dto.packet.client.ZWJoinPacket;
import com.aaemu.zone.service.dto.packet.server.WZJoinResponsePacket;
import com.aaemu.zone.service.model.Account;
import com.aaemu.zone.service.util.ByteBufUtils;
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
    private final ByteBufUtils byteBufUtils;

    @Override
    public void join(ZWJoinPacket packet) {
        accountMap.put(packet.getChannel(), new Account(packet.getAccountId()));
        WZJoinResponsePacket WZJoinResponsePacket = new WZJoinResponsePacket();
        WZJoinResponsePacket.setReason(0);
        WZJoinResponsePacket.setFSet("0"); // siege
        WZJoinResponsePacket.setBf(false);
        packet.getChannel().writeAndFlush(WZJoinResponsePacket.build(byteBufUtils));
    }
}
