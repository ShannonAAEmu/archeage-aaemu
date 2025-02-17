package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestAuth implements ClientPacket {
    private final Channel channel;
    private final int p_from;
    private final int p_to;
    private final byte svc;
    private final boolean dev;
    private final String account;
    private final String mac;
    private final String mac2;
    private final byte[] cpu;

    public CARequestAuth(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.svc = byteBufUtil.readByte(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
        this.account = byteBufUtil.readString(byteBuf);
        this.mac = byteBufUtil.readString(byteBuf);
        this.mac2 = byteBufUtil.readString(byteBuf);
        this.cpu = new byte[8];
        for (byte i = 0; i < this.cpu.length; i++) {
            this.cpu[i] = byteBufUtil.readByte(byteBuf);
        }
    }

}
