package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.service.dto.packet.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ACWorldCookie extends Packet {
    private int cookie;
    private String ip;
    private int port;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACWorldCookie.getOpcode(), byteBuf);
        byteBufUtil.writeD(cookie, byteBuf);
        byteBufUtil.writeS(ip, byteBuf);
        byteBufUtil.writeW(port, byteBuf);
        return byteBuf;
    }
}
