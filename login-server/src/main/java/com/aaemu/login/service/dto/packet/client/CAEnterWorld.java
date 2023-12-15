package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CAEnterWorld implements ClientPacket {
    private final long flag;
    private final int wid;   // World id

    public CAEnterWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.flag = byteBufUtil.readQ(byteBuf);
        this.wid = byteBufUtil.readB(byteBuf);
    }
}
