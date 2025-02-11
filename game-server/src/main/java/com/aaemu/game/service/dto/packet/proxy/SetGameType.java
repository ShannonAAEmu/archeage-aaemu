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
public class SetGameType {
    private String level;
    private long checksum;
    private boolean immersive;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._2, byteBuf);
        byteBufUtils.writeOpcode(ProxyPacket.SET_GAME_TYPE, byteBuf);
        byteBufUtils.writeS(level, byteBuf);
        byteBufUtils.writeQ(checksum, byteBuf);
        byteBufUtils.writeBoolean(immersive, byteBuf);
        return byteBuf;
    }
}
