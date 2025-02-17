package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.enums.fraction.FractionType;
import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.enums.unit.AbilityType;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Shannon
 */
@Data
@RequiredArgsConstructor
public class SCCreateCharacterResponse {
    private final CSCreateCharacter createCharacter;
    private final Map<Channel, Account> accountMap;
    private byte startLevel;
    private int startMoney;

    public void setStartLevel(int startLevel) {
        this.startLevel = (byte) startLevel;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CREATE_CHARACTER_RESPONSE, byteBuf);
        byteBufUtil.writeInt(accountMap.get(createCharacter.getChannel()).getId(), byteBuf);
        byteBufUtil.writeString(StringUtils.capitalize(createCharacter.getName()), byteBuf);
        byteBufUtil.writeByte(createCharacter.getUnitRace().getId(), byteBuf);
        byteBufUtil.writeByte(createCharacter.getUnitGender().getId(), byteBuf);
        byteBufUtil.writeByte(startLevel, byteBuf);
        byteBufUtil.writeInt(100, byteBuf);  // health
        byteBufUtil.writeInt(100, byteBuf);  // mana
        byteBufUtil.writeInt(260, byteBuf);  // zid
        byteBufUtil.writeInt(FractionType.NUIAN.getType(), byteBuf);
        byteBufUtil.writeInt(0, byteBuf);    // type ?
        byteBufUtil.writeInt(0, byteBuf);    // family
        for (int i = 0; i < 28; i++) {
            byteBufUtil.writeInt(0, byteBuf);    // type (items_in_inventory)
        }
        for (AbilityType abilityType : createCharacter.getAbilityList()) {
            byteBufUtil.writeByte(abilityType.getType(), byteBuf);
        }
        byteBufUtil.writeLong(2000, byteBuf); // x
        byteBufUtil.writeLong(2000, byteBuf); // y
        byteBufUtil.writeFloat(136.0f, byteBuf); // z
        byteBufUtil.write(createCharacter.getHeadDetails().build(), byteBuf);
        byteBufUtil.writeShort((short) 50, byteBuf);   // laborPower
        byteBufUtil.writeLong(System.currentTimeMillis(), byteBuf);   // lastLaborPowerModified
        byteBufUtil.writeShort((short) 0, byteBuf);    // deadCount
        byteBufUtil.writeLong(System.currentTimeMillis(), byteBuf);   // deadTime
        byteBufUtil.writeInt(0, byteBuf);    // rezWaitDuration
        byteBufUtil.writeLong(System.currentTimeMillis(), byteBuf);   // rezTime
        byteBufUtil.writeInt(0, byteBuf);    // rezPenaltyDuration
        byteBufUtil.writeLong(System.currentTimeMillis(), byteBuf);   // lastWorldLeaveTime
        byteBufUtil.writeLong(startMoney, byteBuf);    // moneyAmount
        byteBufUtil.writeLong(0, byteBuf);    // moneyAmount ?
        byteBufUtil.writeShort((short) 0, byteBuf);    // crimePoint
        byteBufUtil.writeInt(0, byteBuf);    // crimeRecord
        byteBufUtil.writeShort((short) 0, byteBuf);    // crimeScore
        byteBufUtil.writeLong(0, byteBuf);   // deleteRequestedTime
        byteBufUtil.writeLong(0, byteBuf);   // transferRequestedTime
        byteBufUtil.writeLong(0, byteBuf);    // deleteDelay
        byteBufUtil.writeInt(0, byteBuf);    // consumedLp

        return byteBuf;
    }
}
