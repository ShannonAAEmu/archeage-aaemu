package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.enums.unit.AbilityType;
import com.aaemu.game.service.enums.unit.UnitGender;
import com.aaemu.game.service.enums.unit.UnitRace;
import com.aaemu.game.service.model.unit.HeadDetails;
import com.aaemu.game.service.util.ByteBufUtil;
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
    private final List<Integer> itemList;
    private final HeadDetails headDetails;
    private List<AbilityType> abilityList;
    private byte level;

    public CSCreateCharacter(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.name = byteBufUtil.readString(byteBuf);
        this.unitRace = UnitRace.get(byteBufUtil.readByte(byteBuf));
        this.unitGender = UnitGender.get(byteBufUtil.readByte(byteBuf));
        this.itemList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            itemList.add(byteBufUtil.readInt(byteBuf));
        }
        this.headDetails = new HeadDetails(byteBufUtil, byteBuf);
        this.abilityList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.abilityList.add(AbilityType.get(byteBufUtil.readByte(byteBuf)));
        }
        this.level = byteBufUtil.readByte(byteBuf);
    }
}