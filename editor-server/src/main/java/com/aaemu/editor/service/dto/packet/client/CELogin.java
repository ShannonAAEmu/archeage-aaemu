package com.aaemu.editor.service.dto.packet.client;

import com.aaemu.editor.service.dto.packet.ClientPacket;
import com.aaemu.editor.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CELogin implements ClientPacket {
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final String accountName;

    public CELogin(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.accountName = byteBufUtil.readS(byteBuf);
    }
}
