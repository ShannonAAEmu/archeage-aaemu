package com.aaemu.stream.service.impl;

import com.aaemu.stream.client.GameServer;
import com.aaemu.stream.service.AuthService;
import com.aaemu.stream.service.dto.client.CharacterDto;
import com.aaemu.stream.service.dto.packet.client.CTJoin;
import com.aaemu.stream.service.dto.packet.server.TCJoinResponse;
import com.aaemu.stream.service.model.Account;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtil byteBufUtil;
    private final GameServer gameServer;

    @Override
    public void enterWorld(CTJoin packet, Channel channel) {
        accountMap.replace(channel, new Account(packet.getAccountId()));
        TCJoinResponse joinResponse = new TCJoinResponse();
        joinResponse.setResponse(0);
        channel.writeAndFlush(joinResponse.build(byteBufUtil));
        CharacterDto characterDto = new CharacterDto();
        characterDto.setAccountId(packet.getAccountId());
        gameServer.sendAuth(characterDto);
    }
}
