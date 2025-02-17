package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CARequestAuthTencent implements ClientPacket {
    private final Channel channel;
    private final int p_from;
    private final int p_to;
    private final boolean dev;
    private final int qqno;
    private final short len;
    private final String sig;
    private final String key;
    private final String mac;

    public CARequestAuthTencent(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.dev = byteBufUtil.readBoolean(byteBuf);
        this.qqno = byteBufUtil.readInt(byteBuf);
        this.len = byteBufUtil.readShort(byteBuf);
        this.sig = byteBufUtil.readString(byteBuf);
        this.key = byteBufUtil.readString(byteBuf);
        this.mac = byteBufUtil.readString(byteBuf);
    }
}
