package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CARequestAuthTencent implements ClientPacket {
    private static final int QQNO_LENGTH = 8;
    private final long p_from;
    private final long p_to;
    private final boolean dev;
    private final String qqno;
    private final int len;
    private final String sig;
    private final String key;
    private final String mac;

    public CARequestAuthTencent(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
        this.qqno = byteBufUtil.readS(QQNO_LENGTH, byteBuf);
        this.len = byteBufUtil.readW(byteBuf);
        this.sig = byteBufUtil.readS(byteBuf);
        this.key = byteBufUtil.readS(byteBuf);
        this.mac = byteBufUtil.readS(byteBuf);
    }
}
