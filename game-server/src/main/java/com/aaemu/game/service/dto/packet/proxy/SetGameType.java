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
public class SetGameType {
    private String level;
    private long checksum;
    private boolean immersive;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtil.writeOpcode(ProxyPacket.SET_GAME_TYPE, byteBuf);
        byteBufUtil.writeString(level, byteBuf);
        byteBufUtil.writeLong(checksum, byteBuf);
        byteBufUtil.writeBoolean(immersive, byteBuf);
        return byteBuf;
    }
}
