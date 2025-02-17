package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ProxyPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ChangeState {
    private final int state;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(8);
        byteBufUtil.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.CHANGE_STATE, byteBuf);
        byteBufUtil.writeInt(state, byteBuf);
        return byteBuf;
    }
}
