package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACWorldCookie {
    private int cookie;
    private String ip;
    private int port;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACWorldCookie, byteBuf);
        byteBufUtil.writeD(cookie, byteBuf);
        byteBufUtil.writeS(ip, byteBuf);
        byteBufUtil.writeW(port, byteBuf);
        return byteBuf;
    }
}
