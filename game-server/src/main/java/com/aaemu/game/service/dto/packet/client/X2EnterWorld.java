package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class X2EnterWorld implements ClientPacket {
    private final Channel channel;
    private final int p_from;
    private final int p_to;
    private final int accountId;
    private final int cookie;
    private final int zoneId;
    private final byte tb;

    public X2EnterWorld(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.accountId = byteBufUtil.readInt(byteBuf);
        this.cookie = byteBufUtil.readInt(byteBuf);
        this.zoneId = byteBufUtil.readInt(byteBuf);
        this.tb = byteBufUtil.readByte(byteBuf);
    }
}
