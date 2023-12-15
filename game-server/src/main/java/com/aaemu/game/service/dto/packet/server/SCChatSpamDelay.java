package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class SCChatSpamDelay {
    private float yellDelay;
    private int maxSpamYell;
    private float spamYellDelay;
    private int maxChatLen;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CHAT_SPAM_DELAY, byteBuf);
        byteBufUtil.writeF(yellDelay, byteBuf);
        byteBufUtil.writeD(maxSpamYell, byteBuf);
        byteBufUtil.writeF(spamYellDelay, byteBuf);
        byteBufUtil.writeD(maxChatLen, byteBuf);
        return byteBuf;
    }
}
