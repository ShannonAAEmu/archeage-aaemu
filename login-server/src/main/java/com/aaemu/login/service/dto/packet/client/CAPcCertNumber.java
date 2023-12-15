package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CAPcCertNumber implements ClientPacket {
    private final String num;

    public CAPcCertNumber(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.num = byteBufUtil.readS(byteBuf);
    }
}
