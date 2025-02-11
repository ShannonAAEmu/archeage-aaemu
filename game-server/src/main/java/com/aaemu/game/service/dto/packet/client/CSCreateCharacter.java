package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.enums.unit.UnitAbilityType;
import com.aaemu.game.service.enums.unit.UnitGender;
import com.aaemu.game.service.enums.unit.UnitRace;
import com.aaemu.game.service.model.unit.HeadDetails;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shannon
 */
@Data
public class CSCreateCharacter implements ClientPacket {
    private final Channel channel;
    private final String name;
    private final UnitRace unitRace;
    private final UnitGender unitGender;
    private final List<Long> itemList;
    private final HeadDetails headDetails;
    private List<UnitAbilityType> unitAbilityList;
    private byte level;

    public CSCreateCharacter(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.name = byteBufUtil.readS(byteBuf);
        this.unitRace = UnitRace.get(byteBufUtil.readB(byteBuf));
        this.unitGender = UnitGender.get(byteBufUtil.readB(byteBuf));
        this.itemList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            itemList.add(byteBufUtil.readD(byteBuf));
        }
        this.headDetails = new HeadDetails(byteBufUtil, byteBuf);
        this.unitAbilityList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.unitAbilityList.add(UnitAbilityType.get(byteBufUtil.readB(byteBuf)));
        }
        this.level = byteBufUtil.readB(byteBuf);
    }
}