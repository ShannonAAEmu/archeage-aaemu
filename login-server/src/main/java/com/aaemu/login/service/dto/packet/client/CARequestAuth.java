package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CARequestAuth extends Packet {
    private static final int CPU_LENGTH = 8;
    private final long p_from;
    private final long p_to;
    private final boolean svc;
    private final boolean dev;
    private final String account;
    private final String mac;
    private final String mac2;
    private final String cpu;

    public CARequestAuth(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.svc = byteBufUtil.readBoolean(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
        this.account = byteBufUtil.readString(byteBuf);
        this.mac = byteBufUtil.readString(byteBuf);
        this.mac2 = byteBufUtil.readString(byteBuf);
        this.cpu = byteBufUtil.readString(CPU_LENGTH, byteBuf);
    }

}
