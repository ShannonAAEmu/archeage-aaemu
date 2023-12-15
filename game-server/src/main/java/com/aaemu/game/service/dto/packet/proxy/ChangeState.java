package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ChangeState {
    private int state;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.CHANGE_STATE, byteBuf);
        byteBufUtil.writeD(state, byteBuf);
        return byteBuf;
    }
}
