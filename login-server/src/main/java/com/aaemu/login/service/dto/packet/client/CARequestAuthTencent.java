package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestAuthTencent implements ClientPacket {
    private static final int QQNO_LENGTH = 8;
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final boolean dev;
    private final String qqno;
    private final int len;
    private final String sig;
    private final String key;
    private final String mac;

    public CARequestAuthTencent(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtils.readD(byteBuf);
        this.p_to = byteBufUtils.readD(byteBuf);
        this.dev = byteBufUtils.readBoolean(byteBuf);
        this.qqno = byteBufUtils.readS(QQNO_LENGTH, byteBuf);
        this.len = byteBufUtils.readW(byteBuf);
        this.sig = byteBufUtils.readS(byteBuf);
        this.key = byteBufUtils.readS(byteBuf);
        this.mac = byteBufUtils.readS(byteBuf);
    }
}
