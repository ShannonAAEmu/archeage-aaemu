package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class SCAccountInfo {
    private int payMethod;
    private int payLocation;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_ACCOUNT_INFO, byteBuf);
        byteBufUtil.writeD(payMethod, byteBuf);
        byteBufUtil.writeD(payLocation, byteBuf);
        return byteBuf;
    }
}
