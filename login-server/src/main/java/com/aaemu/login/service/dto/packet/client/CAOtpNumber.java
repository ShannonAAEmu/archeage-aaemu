package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CAOtpNumber implements ClientPacket {
    private final Channel channel;
    private final String num;

    public CAOtpNumber(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf) {
        this.channel = channel;
        this.num = byteBufUtils.readS(byteBuf);
    }
}
