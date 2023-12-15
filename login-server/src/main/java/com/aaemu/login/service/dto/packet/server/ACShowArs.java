package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACShowArs {
    private String num;
    private int timeout;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACShowArs, byteBuf);
        byteBufUtil.writeS(num, byteBuf);
        byteBufUtil.writeD(timeout, byteBuf);
        return byteBuf;
    }
}
