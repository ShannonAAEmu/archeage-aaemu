package com.aaemu.stream.service.impl;

import com.aaemu.stream.client.GameServer;
import com.aaemu.stream.service.AuthService;
import com.aaemu.stream.service.dto.client.StreamAccountDto;
import com.aaemu.stream.service.dto.packet.client.CTJoin;
import com.aaemu.stream.service.dto.packet.server.TCJoinResponse;
import com.aaemu.stream.service.model.StreamAccount;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, StreamAccount> accountMap;
    private final ByteBufUtil byteBufUtil;
    private final GameServer gameServer;

    @Override
    public void enterWorld(CTJoin packet, Channel channel) {
        StreamAccount streamAccount = new StreamAccount(packet.getAccountId());
        streamAccount.setCookie(packet.getCookie());
        accountMap.replace(channel, streamAccount);
        TCJoinResponse joinResponse = new TCJoinResponse();
        joinResponse.setResponse(0);
        channel.writeAndFlush(joinResponse.build(byteBufUtil));
        StreamAccountDto streamAccountDto = new StreamAccountDto();
        streamAccountDto.setId(packet.getAccountId());
        streamAccountDto.setCookie(packet.getCookie());
        gameServer.sendAuth(streamAccountDto);
    }
}
