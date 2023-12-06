package com.aaemu.login.service.entity.packet.client;

import com.aaemu.login.service.entity.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAOtpNumber extends Packet {
    private String num;

    public CAOtpNumber(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.num = byteBufUtil.readString(byteBuf);
    }
}
