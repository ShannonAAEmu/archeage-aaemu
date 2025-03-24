package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.enums.unit.Ability;
import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import com.aaemu.game.service.model.unit.HeadDetails;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CSCreateCharacter implements ClientPacket {
    private final Channel channel;
    private final String name;
    private final Race race;
    private final Gender gender;
    private final BodyParts bodyParts;
    private final HeadDetails headDetails;
    private Ability ability_1;
    private Ability ability_2;
    private Ability ability_3;
    private byte level;

    public CSCreateCharacter(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.name = byteBufUtil.readString(byteBuf);
        this.race = Race.getById(byteBufUtil, byteBuf);
        this.gender = Gender.getById(byteBufUtil, byteBuf);
        this.bodyParts = new BodyParts(byteBufUtil, byteBuf);
        this.headDetails = new HeadDetails(byteBufUtil, byteBuf);
        this.ability_1 = Ability.getById(byteBufUtil, byteBuf);
        this.ability_2 = Ability.getById(byteBufUtil, byteBuf);
        this.ability_3 = Ability.getById(byteBufUtil, byteBuf);
        this.level = byteBufUtil.readByte(byteBuf);
    }
}