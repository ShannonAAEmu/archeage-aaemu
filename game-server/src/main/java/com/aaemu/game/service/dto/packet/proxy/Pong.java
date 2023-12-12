package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.service.dto.packet.ProxyPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Pong extends Packet {
    private long tm;
    private long when;
    private long elapsed;
    private long remote;
    private int local;
    private int world;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(2, byteBuf);
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
