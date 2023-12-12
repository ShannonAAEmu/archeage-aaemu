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
public class SetGameType extends Packet {
    private String level;
    private long checksum;
    private boolean immersive;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.SET_GAME_TYPE, byteBuf);
        byteBufUtil.writeS(level, byteBuf);
        byteBufUtil.writeQ(checksum, byteBuf);
        byteBufUtil.writeBoolean(immersive, byteBuf);
        return byteBuf;
    }
}
