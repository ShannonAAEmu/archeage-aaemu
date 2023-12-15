package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACChallenge {
    private int salt;
    private int ch;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACChallenge, byteBuf);
        byteBufUtil.writeD(salt, byteBuf);
        for (int i = 0; i < 4; i++) {
            byteBufUtil.writeD(ch, byteBuf);
        }
        return byteBuf;
    }
}
