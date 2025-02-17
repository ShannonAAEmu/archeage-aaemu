package com.aaemu.stream.service.dto.packet.client;

import com.aaemu.stream.service.dto.packet.ClientPacket;
import com.aaemu.stream.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CTJoin implements ClientPacket {
    private final Channel channel;
    private final int accountId;
    private final int cookie;

    public CTJoin(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.accountId = byteBufUtil.readInt(byteBuf);
        this.cookie = byteBufUtil.readInt(byteBuf);
    }
}
