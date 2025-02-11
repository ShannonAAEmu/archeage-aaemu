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
public class SCChatSpamDelay {
    private float yellDelay;
    private int maxSpamYell;
    private float spamYellDelay;
    private int maxChatLen;

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_CHAT_SPAM_DELAY, byteBuf);
        byteBufUtils.writeF(yellDelay, byteBuf);
        byteBufUtils.writeD(maxSpamYell, byteBuf);
        byteBufUtils.writeF(spamYellDelay, byteBuf);
        byteBufUtils.writeD(maxChatLen, byteBuf);
        return byteBuf;
    }
}
