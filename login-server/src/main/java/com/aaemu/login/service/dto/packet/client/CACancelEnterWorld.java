package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CACancelEnterWorld implements ClientPacket {
    private final int wid;   // World id

    public CACancelEnterWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.wid = byteBufUtil.readB(byteBuf);
    }
}
