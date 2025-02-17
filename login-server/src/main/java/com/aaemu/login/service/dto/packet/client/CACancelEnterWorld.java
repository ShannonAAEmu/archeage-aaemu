package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CACancelEnterWorld implements ClientPacket {
    private final Channel channel;
    private final byte worldId;   // wid

    public CACancelEnterWorld(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.worldId = byteBufUtil.readByte(byteBuf);
    }
}
