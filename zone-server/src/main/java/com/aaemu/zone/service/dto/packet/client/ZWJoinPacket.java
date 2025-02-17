package com.aaemu.zone.service.dto.packet.client;

import com.aaemu.zone.service.dto.packet.ClientPacket;
import com.aaemu.zone.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class ZWJoinPacket implements ClientPacket {
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final long id;
    private final long ip;
    private final int port;
    private final long accountId;
    private final long instanceId;
    private final boolean dev;

    public ZWJoinPacket(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.id = byteBufUtil.readInt(byteBuf);
        this.ip = byteBufUtil.readInt(byteBuf);
        this.port = byteBufUtil.readShort(byteBuf);
        this.accountId = byteBufUtil.readInt(byteBuf);
        this.instanceId = byteBufUtil.readInt(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
    }
}
