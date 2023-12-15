package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;

@Data
public class SCRefreshInCharacterList {
    private List<Byte> con;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_REFRESH_IN_CHARACTER_LIST, byteBuf);
        for (int i = 0; i < 9; i++) {
            byteBufUtil.writeB(con.get(i), byteBuf);
        }
        return byteBuf;
    }
}
