package com.aaemu.login.service.entity.packet.server;

import com.aaemu.login.service.entity.packet.Packet;
import com.aaemu.login.service.entity.packet.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ACChallenge extends Packet {
    private int salt;
    private int ch;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACChallenge.getOpcode(), byteBuf);
        byteBufUtil.writeD(salt, byteBuf);
        for (int i = 0; i < 4; i++) {
            byteBufUtil.writeD(ch, byteBuf);
        }
        return byteBuf;
    }
}
