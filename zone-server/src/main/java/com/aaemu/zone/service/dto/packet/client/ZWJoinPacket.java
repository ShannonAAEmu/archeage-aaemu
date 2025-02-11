package com.aaemu.zone.service.dto.packet.client;

import com.aaemu.zone.service.dto.packet.ClientPacket;
import com.aaemu.zone.service.util.ByteBufUtils;
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

    public ZWJoinPacket(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.id = byteBufUtil.readD(byteBuf);
        this.ip = byteBufUtil.readD(byteBuf);
        this.port = byteBufUtil.readW(byteBuf);
        this.accountId = byteBufUtil.readD(byteBuf);
        this.instanceId = byteBufUtil.readD(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
    }
}
