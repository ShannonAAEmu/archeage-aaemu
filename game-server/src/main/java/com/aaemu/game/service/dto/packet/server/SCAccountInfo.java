package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCAccountInfo {
    private int payMethod;
    private int payLocation;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_ACCOUNT_INFO, byteBuf);
        byteBufUtils.writeD(payMethod, byteBuf);
        byteBufUtils.writeD(payLocation, byteBuf);
        return byteBuf;
    }
}
