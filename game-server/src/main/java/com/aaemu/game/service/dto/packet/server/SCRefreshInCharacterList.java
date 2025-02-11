package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;

/**
 * @author Shannon
 */
@Data
public class SCRefreshInCharacterList {
    private List<Byte> con;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_REFRESH_IN_CHARACTER_LIST, byteBuf);
        for (int i = 0; i < 9; i++) {
            byteBufUtils.writeB(con.get(i), byteBuf);
        }
        return byteBuf;
    }
}
