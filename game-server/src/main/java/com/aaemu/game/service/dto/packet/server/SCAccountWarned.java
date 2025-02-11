package com.aaemu.game.service.dto.packet.server;

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
public class SCAccountWarned {
    private byte source;
    private String msg;

    public void setSource(int source) {
        this.source = (byte) source;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_ACCOUNT_WARNED, byteBuf);
        byteBufUtils.writeB(source, byteBuf);
        byteBufUtils.writeS(msg, byteBuf);
        return byteBuf;
    }
}
