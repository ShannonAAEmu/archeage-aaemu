package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACAuthResponse {
    private int accountId;
    private String wsk;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACAuthResponse, byteBuf);
        byteBufUtil.writeD(accountId, byteBuf);
        byteBufUtil.writeS(wsk, byteBuf);
        return byteBuf;
    }
}
