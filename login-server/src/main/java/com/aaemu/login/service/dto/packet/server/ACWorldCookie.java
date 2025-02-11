package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACWorldCookie {
    private int cookie;
    private String ip;
    private int port;

    public ByteBuf build(ByteBufUtils byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(10 + ip.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.ACWorldCookie, byteBuf);
        byteBufUtil.writeD(cookie, byteBuf);
        byteBufUtil.writeS(ip, byteBuf);
        byteBufUtil.writeW(port, byteBuf);
        return byteBuf;
    }
}
