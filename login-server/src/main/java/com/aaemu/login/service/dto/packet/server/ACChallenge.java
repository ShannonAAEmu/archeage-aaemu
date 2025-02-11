package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACChallenge {
    private int salt;
    private int ch;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(22);
        byteBufUtils.writeOpcode(ServerPacket.ACChallenge, byteBuf);
        byteBufUtils.writeD(salt, byteBuf);
        for (int i = 0; i < 4; i++) {
            byteBufUtils.writeD(ch, byteBuf);
        }
        return byteBuf;
    }
}
