package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ProxyPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Pong {
    private long tm;
    private long when;
    private long elapsed;
    private long remote;
    private int local;
    private int world;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.PONG, byteBuf);
        byteBufUtil.writeLong(tm, byteBuf);
        byteBufUtil.writeLong(when, byteBuf);
        byteBufUtil.writeLong(elapsed, byteBuf);
        byteBufUtil.writeLong(remote, byteBuf);
        byteBufUtil.writeInt(local, byteBuf);
        byteBufUtil.writeInt(world, byteBuf);
        return byteBuf;
    }
}
