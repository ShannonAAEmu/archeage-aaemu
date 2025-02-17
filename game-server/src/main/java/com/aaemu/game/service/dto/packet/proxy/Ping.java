package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Ping implements ClientPacket {
    private final Channel channel;
    private final long tm;
    private final long when;
    private final int local;

    public Ping(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.tm = byteBufUtil.readLong(byteBuf);
        this.when = byteBufUtil.readLong(byteBuf);
        this.local = byteBufUtil.readInt(byteBuf);
    }
}
