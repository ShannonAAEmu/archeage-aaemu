package com.aaemu.game.service.impl;

import com.aaemu.game.service.GameServerService;
import com.aaemu.game.service.ItemService;
import com.aaemu.game.service.ZoneService;
import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSDeleteCharacter;
import com.aaemu.game.service.dto.packet.client.CSLeaveWorld;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.server.BroadcastVisualOption;
import com.aaemu.game.service.dto.packet.server.SCAccountWarned;
import com.aaemu.game.service.dto.packet.server.SCCreateCharacterResponse;
import com.aaemu.game.service.dto.packet.server.SCReconnectAuth;
import com.aaemu.game.service.dto.packet.server.SCRefreshInCharacterList;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.util.ByteBufUtil;
import com.aaemu.game.service.util.ValidatorUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class GameServiceImpl implements GameServerService {
    private final Map<Channel, Account> accountMap;
    private final ValidatorUtil validatorUtil;
    private final ByteBufUtil byteBufUtil;
    private final ZoneService zoneService;
    private final ItemService itemService;

    @Value("${game_server.config.welcome_msg}")
    private String welcomeMsg;

    @Value("${game_server.config.start.level}")
    private int startLevel;

    @Value("${game_server.config.start.money}")
    private int startMoney;

    @Value("${game_server.config.start.clothEquip}")
    private boolean addStartupClothEquip;

    @Value("${game_server.config.start.weaponEquip}")
    private boolean addStartupWeaponEquip;

    @Override
    public void broadcastVisualOption(CSBroadcastVisualOption packet) {
        BroadcastVisualOption broadcastVisualOption = new BroadcastVisualOption();
        broadcastVisualOption.setVoptFlag(packet.getVOptFlag());
        broadcastVisualOption.setStpList(packet.getStpList());
        broadcastVisualOption.setHelmet(packet.isHelmet());
        accountMap.get(packet.getChannel()).setBroadcastVisualOption(broadcastVisualOption);
    }

    @Override
    public void refreshInCharacterList(CSRefreshInCharacterList packet) {
        SCRefreshInCharacterList refreshInCharacterList = new SCRefreshInCharacterList();
        List<Byte> conList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            conList.add((byte) 0);  // TODO race congestion
        }
        refreshInCharacterList.setCon(conList);
        packet.getChannel().writeAndFlush(refreshInCharacterList.build(byteBufUtil));
        processAccountWelcomeMsg(packet.getChannel());
    }

    @Override
    public void sendAccountWarned(Channel channel, int source, String msg) {
        SCAccountWarned accountWarned = new SCAccountWarned();
        accountWarned.setSource(source);
        accountWarned.setMsg(msg);
        channel.writeAndFlush(accountWarned.build(byteBufUtil));
    }

    @Override
    public void leaveWorld(CSLeaveWorld packet) {
        packet.getChannel().writeAndFlush(new SCReconnectAuth(accountMap.get(packet.getChannel()).getCookie()).build(byteBufUtil));
    }

    @Override
    public void createCharacter(CSCreateCharacter packet) {
        if (!validatorUtil.isValidForCreate(packet)) {
            return;
        }
        SCCreateCharacterResponse createCharacterResponse = new SCCreateCharacterResponse(packet, accountMap);
        createCharacterResponse.setZoneService(zoneService);
        createCharacterResponse.setItemService(itemService);
        createCharacterResponse.setAddStartupClothEquip(addStartupClothEquip);
        createCharacterResponse.setAddStartupWeaponEquip(addStartupWeaponEquip);
        createCharacterResponse.setStartLevel(startLevel);
        createCharacterResponse.setStartMoney(startMoney);
        packet.getChannel().writeAndFlush(createCharacterResponse.build(byteBufUtil));
    }

    @Override
    public void deleteCharacter(CSDeleteCharacter packet) {
        // TODO delete character
    }

    private void processAccountWelcomeMsg(Channel channel) {
        Account account = accountMap.get(channel);
        if (Objects.isNull(account.getWarnedSendTime())) {
            account.setWarnedSendTime(OffsetDateTime.now());
            sendAccountWarned(channel, 0, welcomeMsg);
        } else {
            if (Duration.between(account.getWarnedSendTime(), OffsetDateTime.now()).getSeconds() > 30) {
                sendAccountWarned(channel, 0, welcomeMsg);
            }
        }
    }
}
