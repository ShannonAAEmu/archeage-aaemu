package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CSDeleteCharacter implements ClientPacket {
    private final Channel channel;
    private final int id;   // type

    public CSDeleteCharacter(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.id = byteBufUtil.readInt(byteBuf);
    }
}
