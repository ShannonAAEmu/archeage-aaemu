package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.service.dto.packet.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SCAccountInfo extends Packet {
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
