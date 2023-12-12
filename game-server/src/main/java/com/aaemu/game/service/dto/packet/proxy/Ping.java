package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Ping extends Packet {
    private final long tm;
    private final long when;
    private final int local;

    public Ping(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.tm = byteBufUtil.readQ(byteBuf);
        this.when = byteBufUtil.readQ(byteBuf);
        this.local = (int) byteBufUtil.readD(byteBuf);
    }
}
