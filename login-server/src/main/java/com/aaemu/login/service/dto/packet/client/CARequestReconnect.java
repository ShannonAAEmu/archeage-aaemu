package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestReconnect implements ClientPacket {
    private final Channel channel;
    private final int p_from;
    private final int p_to;
    private final int accountId;  // aid
    private final byte worldId;   // wid
    private final int cookie;
    private final String mac;

    public CARequestReconnect(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.accountId = byteBufUtil.readInt(byteBuf);
        this.worldId = byteBufUtil.readByte(byteBuf);
        this.cookie = byteBufUtil.readInt(byteBuf);
        this.mac = byteBufUtil.readString(byteBuf);
    }
}
