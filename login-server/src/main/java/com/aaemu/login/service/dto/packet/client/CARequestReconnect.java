package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CARequestReconnect extends Packet {
    private final long p_from;
    private final long p_to;
    private final long aid; // Account id
    private final int wid;   // World id
    private final long cookie;
    private final String mac;

    public CARequestReconnect(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.aid = byteBufUtil.readD(byteBuf);
        this.wid = byteBufUtil.readB(byteBuf);
        this.cookie = byteBufUtil.readD(byteBuf);
        this.mac = byteBufUtil.readString(byteBuf);
    }
}
