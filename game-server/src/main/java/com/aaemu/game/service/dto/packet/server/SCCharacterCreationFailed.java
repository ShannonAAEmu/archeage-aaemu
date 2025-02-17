package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.CharacterCreationError;
import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCCharacterCreationFailed {
    private CharacterCreationError reason;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CHARACTER_CREATION_FAILED, byteBuf);
        byteBufUtil.writeByte(reason.getReason(), byteBuf);
        return byteBuf;
    }
}
