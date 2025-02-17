package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACWorldQueue {
    private byte worldId;   // wid
    private int turnCount;
    private int totalCount;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(7);
        byteBufUtil.writeOpcode(ServerPacket.AC_WORLD_QUEUE, byteBuf);
        byteBufUtil.writeByte(worldId, byteBuf);
        byteBufUtil.writeShort(turnCount, byteBuf);
        byteBufUtil.writeShort(totalCount, byteBuf);
        return byteBuf;
    }
}
