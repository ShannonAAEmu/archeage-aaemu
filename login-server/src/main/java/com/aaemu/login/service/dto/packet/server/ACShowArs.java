package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACShowArs {
    private final String num;
    private final int timeout;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(8 + num.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.AC_SHOW_ARS, byteBuf);
        byteBufUtil.writeString(num, byteBuf);
        byteBufUtil.writeInt(timeout, byteBuf);
        return byteBuf;
    }
}
