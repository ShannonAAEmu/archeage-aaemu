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
public class ACChallenge2 extends Packet {
    private int round;
    private String salt;
    private int ch;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACChallenge2, byteBuf);
        byteBufUtil.writeD(round, byteBuf);
        byteBufUtil.writeS(salt, byteBuf);
        for (int i = 0; i < 8; i++) {
            byteBufUtil.writeD(ch, byteBuf);
        }
        return byteBuf;
    }
}
