package com.aaemu.game.service.impl;

import com.aaemu.game.service.GameServerService;
import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSLeaveWorld;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.server.BroadcastVisualOption;
import com.aaemu.game.service.dto.packet.server.SCAccountWarned;
import com.aaemu.game.service.dto.packet.server.SCCreateCharacterResponse;
import com.aaemu.game.service.dto.packet.server.SCRefreshInCharacterList;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.util.ByteBufUtils;
import com.aaemu.game.service.util.ValidatorUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class GameServerServiceImpl implements GameServerService {
    private final Map<Channel, Account> accountMap;
    private final ValidatorUtils validatorUtils;
    private final ByteBufUtils byteBufUtils;

    @Override
    public void broadcastVisualOption(CSBroadcastVisualOption packet) {
        BroadcastVisualOption broadcastVisualOption = new BroadcastVisualOption();
        broadcastVisualOption.setVoptFlag(packet.getVoptFlag());
        broadcastVisualOption.setStpList(packet.getStpList());
        broadcastVisualOption.setHelmet(packet.isHelmet());
        accountMap.get(packet.getChannel()).setBroadcastVisualOption(broadcastVisualOption);
    }

    @Override
    public void refreshInCharacterList(CSRefreshInCharacterList packet) {
        SCRefreshInCharacterList refreshInCharacterList = new SCRefreshInCharacterList();
        List<Byte> conList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            conList.add((byte) 0);
        }
        refreshInCharacterList.setCon(conList);
        packet.getChannel().writeAndFlush(refreshInCharacterList.build(byteBufUtils));
        sendAccountWarned(packet.getChannel(), 0, "Welcome to AAEMU Java server");
    }

    @Override
    public void sendAccountWarned(Channel channel, int source, String msg) {
        SCAccountWarned accountWarned = new SCAccountWarned();
        accountWarned.setSource(source);
        accountWarned.setMsg(msg);
        channel.writeAndFlush(accountWarned.build(byteBufUtils));
    }

    @Override
    public void leaveWorld(CSLeaveWorld packet) {
        // TODO
    }

    @Override
    public void createCharacter(CSCreateCharacter packet) {
        System.out.println(packet.getHeadDetails().getFace());
        if (!validatorUtils.isValidForCreate(packet)) {
            return;
        }
        SCCreateCharacterResponse createCharacterResponse = new SCCreateCharacterResponse(packet);
        packet.getChannel().writeAndFlush(createCharacterResponse.build(byteBufUtils));
    }
}
