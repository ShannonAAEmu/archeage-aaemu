package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACChallenge2 {
    private int round;
    private String salt;
    private int ch;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(40 + salt.getBytes().length);
        byteBufUtils.writeOpcode(ServerPacket.ACChallenge2, byteBuf);
        byteBufUtils.writeD(round, byteBuf);
        byteBufUtils.writeS(salt, byteBuf);
        for (int i = 0; i < 8; i++) {
            byteBufUtils.writeD(ch, byteBuf);
        }
        return byteBuf;
    }
}
