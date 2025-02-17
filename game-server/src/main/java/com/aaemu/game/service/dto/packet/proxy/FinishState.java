package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class FinishState implements ClientPacket {
    private final Channel channel;
    private int state;

    public FinishState(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.state = byteBufUtil.readInt(byteBuf);
    }
}
