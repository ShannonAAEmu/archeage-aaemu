package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtils;
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

    public Ping(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf) {
        this.channel = channel;
        this.tm = byteBufUtils.readQ(byteBuf);
        this.when = byteBufUtils.readQ(byteBuf);
        this.local = (int) byteBufUtils.readD(byteBuf);
    }
}
