package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACAuthResponse {
    private int accountId;
    private String wsk;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(8 + wsk.getBytes().length);
        byteBufUtils.writeOpcode(ServerPacket.ACAuthResponse, byteBuf);
        byteBufUtils.writeD(accountId, byteBuf);
        byteBufUtils.writeS(wsk, byteBuf);
        return byteBuf;
    }
}
