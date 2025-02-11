package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ChangeState {
    private int state;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(8);
        byteBufUtils.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtils.writeOpcode(ProxyPacket.CHANGE_STATE, byteBuf);
        byteBufUtils.writeD(state, byteBuf);
        return byteBuf;
    }
}
