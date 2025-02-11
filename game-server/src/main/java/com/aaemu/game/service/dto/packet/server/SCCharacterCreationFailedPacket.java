package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.CharacterCreationError;
import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCCharacterCreationFailedPacket {
    private CharacterCreationError reason;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_CHARACTER_CREATION_FAILED, byteBuf);
        byteBufUtils.writeW(reason.getReason(), byteBuf);
        return byteBuf;
    }
}
