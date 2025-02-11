package com.aaemu.stream.service.impl;

import com.aaemu.stream.service.AuthService;
import com.aaemu.stream.service.GameServerService;
import com.aaemu.stream.service.dto.packet.client.CTJoin;
import com.aaemu.stream.service.dto.packet.server.TCJoinResponse;
import com.aaemu.stream.service.model.Account;
import com.aaemu.stream.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtils byteBufUtils;
    private final GameServerService gameServerService;

    @Override
    public void join(CTJoin packet) {
        accountMap.put(packet.getChannel(), new Account(packet.getAccountId()));
        packet.getChannel().writeAndFlush(new TCJoinResponse((byte) 0).build(byteBufUtils));
        gameServerService.join(packet.getAccountId());
    }
}
