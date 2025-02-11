package com.aaemu.stream.service.dto.packet.client;

import com.aaemu.stream.service.dto.packet.ClientPacket;
import com.aaemu.stream.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CTJoin implements ClientPacket {
    private final Channel channel;
    private final long accountId;
    private final long cookie;

    public CTJoin(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf) {
        this.channel = channel;
        this.accountId = byteBufUtils.readD(byteBuf);
        this.cookie = byteBufUtils.readD(byteBuf);
    }
}
