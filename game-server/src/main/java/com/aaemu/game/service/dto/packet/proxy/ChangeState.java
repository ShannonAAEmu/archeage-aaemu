package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.service.dto.packet.ProxyPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeState extends Packet {
    private int state;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.CHANGE_STATE, byteBuf);
        byteBufUtil.writeD(state, byteBuf);
        return byteBuf;
    }
}
