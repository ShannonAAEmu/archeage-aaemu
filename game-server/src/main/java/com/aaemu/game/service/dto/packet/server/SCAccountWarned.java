package com.aaemu.game.service.dto.packet.server;

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
public class SCAccountWarned {
    private byte source;
    private String msg;

    public void setSource(int source) {
        this.source = (byte) source;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_ACCOUNT_WARNED, byteBuf);
        byteBufUtil.writeByte(source, byteBuf);
        byteBufUtil.writeString(msg, byteBuf);
        return byteBuf;
    }
}
