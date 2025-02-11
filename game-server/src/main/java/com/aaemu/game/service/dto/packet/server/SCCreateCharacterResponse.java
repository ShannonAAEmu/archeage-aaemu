package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@Data
@RequiredArgsConstructor
public class SCCreateCharacterResponse {
    private final CSCreateCharacter character;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_CREATE_CHARACTER_RESPONSE, byteBuf);

        byteBufUtils.writeD(1, byteBuf);
        byteBufUtils.writeS(character.getName(), byteBuf);
        byteBufUtils.writeB((byte) character.getUnitRace().getId(), byteBuf);
        byteBufUtils.writeB((byte) character.getUnitGender().getId(), byteBuf);
        byteBufUtils.writeB(character.getLevel(), byteBuf);
        byteBufUtils.writeD(100, byteBuf);  // health
        byteBufUtils.writeD(100, byteBuf);  // mana
        byteBufUtils.writeD(177, byteBuf);  // zid
        byteBufUtils.writeD(0, byteBuf);    // type
        byteBufUtils.writeD(0, byteBuf);    // type
        byteBufUtils.writeD(0, byteBuf);    // family
        byteBufUtils.writeD(0, byteBuf);    // items_in_inventory
        byteBufUtils.writeB((byte) character.getUnitAbilityList().getFirst().getType(), byteBuf);
        byteBufUtils.writeB((byte) character.getUnitAbilityList().get(1).getType(), byteBuf);
        byteBufUtils.writeB((byte) character.getUnitAbilityList().getLast().getType(), byteBuf);
        byteBufUtils.writeQ(0, byteBuf); // x
        byteBufUtils.writeQ(0, byteBuf); // y
        byteBufUtils.writeF(1.0f, byteBuf); // z
        byteBufUtils.writeB(character.getHeadDetails().getCustomModelType().getType(), byteBuf);
        byteBufUtils.write(character.getHeadDetails().getFace().build(), byteBuf);
        byteBufUtils.writeW(50, byteBuf);   // laborPower
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // lastLaborPowerModified
        byteBufUtils.writeW(0, byteBuf);    // deadCount
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // deadTime
        byteBufUtils.writeD(0, byteBuf);    // rezWaitDuration
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // rezTime
        byteBufUtils.writeD(0, byteBuf);    // rezPenaltyDuration
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // lastWorldLeaveTime
        byteBufUtils.writeQ(0, byteBuf);    // moneyAmount
        byteBufUtils.writeQ(0, byteBuf);    // moneyAmount
        byteBufUtils.writeW(0, byteBuf);    // crimePoint
        byteBufUtils.writeD(0, byteBuf);    // crimeRecord
        byteBufUtils.writeW(0, byteBuf);    // crimeScore
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // deleteRequestedTime
        byteBufUtils.writeQ(System.currentTimeMillis(), byteBuf);   // transferRequestedTime
        byteBufUtils.writeQ(0, byteBuf);    // deleteDelay
        byteBufUtils.writeD(0, byteBuf);    // consumedLp

        return byteBuf;
    }
}
