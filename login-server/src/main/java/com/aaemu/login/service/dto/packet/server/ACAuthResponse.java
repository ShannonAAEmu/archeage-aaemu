package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACAuthResponse {
    private int accountId;
    private String wsk;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(8 + wsk.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.AC_AUTH_RESPONSE, byteBuf);
        byteBufUtil.writeInt(accountId, byteBuf);
        byteBufUtil.writeString(wsk, byteBuf);
        return byteBuf;
    }
}
