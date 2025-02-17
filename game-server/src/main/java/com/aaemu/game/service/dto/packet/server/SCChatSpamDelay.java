package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCChatSpamDelay {
    private float yellDelay;
    private int maxSpamYell;
    private float spamYellDelay;
    private int maxChatLen;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CHAT_SPAM_DELAY, byteBuf);
        byteBufUtil.writeFloat(yellDelay, byteBuf);
        byteBufUtil.writeInt(maxSpamYell, byteBuf);
        byteBufUtil.writeFloat(spamYellDelay, byteBuf);
        byteBufUtil.writeInt(maxChatLen, byteBuf);
        return byteBuf;
    }
}
