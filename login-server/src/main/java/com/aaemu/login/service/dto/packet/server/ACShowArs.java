package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACShowArs {
    private final String num;
    private final int timeout;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(8 + num.getBytes().length);
        byteBufUtils.writeOpcode(ServerPacket.ACShowArs, byteBuf);
        byteBufUtils.writeS(num, byteBuf);
        byteBufUtils.writeD(timeout, byteBuf);
        return byteBuf;
    }
}
