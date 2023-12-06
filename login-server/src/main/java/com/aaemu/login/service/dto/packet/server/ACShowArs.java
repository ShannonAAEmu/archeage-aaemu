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
public class ACShowArs extends Packet {
    private String num;
    private int timeout;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACShowArs.getOpcode(), byteBuf);
        byteBufUtil.writeS(num, byteBuf);
        byteBufUtil.writeD(timeout, byteBuf);
        return byteBuf;
    }
}
