package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.pay.PayLocation;
import com.aaemu.game.service.enums.pay.PayMethod;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCAccountInfo {
    private PayMethod payMethod;
    private PayLocation payLocation;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_ACCOUNT_INFO, byteBuf);
        byteBufUtil.writeInt(payMethod.getCode(), byteBuf);
        byteBufUtil.writeInt(payLocation.getCode(), byteBuf);
        return byteBuf;
    }
}
