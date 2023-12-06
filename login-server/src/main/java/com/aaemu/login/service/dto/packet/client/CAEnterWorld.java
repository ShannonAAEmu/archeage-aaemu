package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAEnterWorld extends Packet {
    private final long flag;
    private final int wid;   // World id

    public CAEnterWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.flag = byteBufUtil.readQ(byteBuf);
        this.wid = byteBufUtil.readB(byteBuf);
    }
}
