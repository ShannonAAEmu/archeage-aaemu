package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CACancelEnterWorld implements ClientPacket {
    private final Channel channel;
    private final int wid;   // World id

    public CACancelEnterWorld(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.wid = byteBufUtil.readB(byteBuf);
    }
}
