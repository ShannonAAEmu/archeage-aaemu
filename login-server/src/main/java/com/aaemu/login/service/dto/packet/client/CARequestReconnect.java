package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestReconnect implements ClientPacket {
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final long aid; // Account id
    private final int wid;   // World id
    private final long cookie;
    private final String mac;

    public CARequestReconnect(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtils.readD(byteBuf);
        this.p_to = byteBufUtils.readD(byteBuf);
        this.aid = byteBufUtils.readD(byteBuf);
        this.wid = byteBufUtils.readB(byteBuf);
        this.cookie = byteBufUtils.readD(byteBuf);
        this.mac = byteBufUtils.readS(byteBuf);
    }
}
