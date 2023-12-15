package com.aaemu.game.service.impl;

import com.aaemu.game.service.GameService;
import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.model.BroadcastVisualOption;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtil byteBufUtil;

    @Override
    public void setBroadcastVisualOption(CSBroadcastVisualOption packet, Channel channel) {
        BroadcastVisualOption broadcastVisualOption = new BroadcastVisualOption();
        broadcastVisualOption.setVoptFlag(packet.getVoptFlag());
        broadcastVisualOption.setStpList(packet.getStpList());
        broadcastVisualOption.setHelmet(packet.isHelmet());
        accountMap.get(channel).setBroadcastVisualOption(broadcastVisualOption);
    }

    @Override
    public void createCharacter(CSCreateCharacter packet, Channel channel) {
        log.info(packet.toString());     // TODO create character
    }

    @Override
    public void sendAccountWarned(Channel channel) {
        // TODO drop fix
        log.warn("Account warned packet drop client. Need fix this.");
//        SCAccountWarned accountWarned = new SCAccountWarned();
//        accountWarned.setSource(0);
//        accountWarned.setMsg("Welcome to AAEMU Java server");
//        channel.writeAndFlush(accountWarned.build(byteBufUtil));
    }
}
