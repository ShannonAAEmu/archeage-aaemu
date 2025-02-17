package com.aaemu.editor.service.dto.packet.client;

import com.aaemu.editor.service.dto.packet.ClientPacket;
import com.aaemu.editor.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CELogin implements ClientPacket {
    private final Channel channel;
    private final int p_from;
    private final int p_to;
    private final String accountName;

    public CELogin(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.p_from = byteBufUtil.readInt(byteBuf);
        this.p_to = byteBufUtil.readInt(byteBuf);
        this.accountName = byteBufUtil.readS(byteBuf);
    }
}
