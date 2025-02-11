package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestAuth implements ClientPacket {
    private static final int CPU_LENGTH = 8;
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final boolean svc;
    private final boolean dev;
    private final String account;
    private final String mac;
    private final String mac2;
    private final String cpu;

    public CARequestAuth(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.svc = byteBufUtil.readBoolean(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
        this.account = byteBufUtil.readS(byteBuf);
        this.mac = byteBufUtil.readS(byteBuf);
        this.mac2 = byteBufUtil.readS(byteBuf);
        this.cpu = byteBufUtil.readS(CPU_LENGTH, byteBuf);
    }

}
