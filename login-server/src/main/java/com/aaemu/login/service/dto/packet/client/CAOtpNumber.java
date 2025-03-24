package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CAOtpNumber implements ClientPacket {
    private final Channel channel;
    private final String number;    // num

    public CAOtpNumber(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.number = byteBufUtil.readString(byteBuf);
    }

    public CAOtpNumber(CATestArs packet) {
        this.channel = packet.getChannel();
        this.number = packet.getNum();
    }
}
