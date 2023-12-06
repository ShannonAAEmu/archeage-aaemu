package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAPcCertNumber extends Packet {
    private final String num;

    public CAPcCertNumber(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.num = byteBufUtil.readString(byteBuf);
    }
}
