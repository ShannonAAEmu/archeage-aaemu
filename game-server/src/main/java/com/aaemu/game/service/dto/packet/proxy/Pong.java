package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.service.util.ByteBufUtils;
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

    public ByteBuf build(ByteBufUtils byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.PONG, byteBuf);
        byteBufUtil.writeQ(tm, byteBuf);
        byteBufUtil.writeQ(when, byteBuf);
        byteBufUtil.writeQ(elapsed, byteBuf);
        byteBufUtil.writeQ(remote, byteBuf);
        byteBufUtil.writeD(local, byteBuf);
        byteBufUtil.writeD(world, byteBuf);
        return byteBuf;
    }
}
