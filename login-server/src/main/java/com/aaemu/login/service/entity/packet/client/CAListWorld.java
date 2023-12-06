package com.aaemu.login.service.entity.packet.client;

import com.aaemu.login.service.entity.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAListWorld extends Packet {
    private long flag;

    public CAListWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.flag = byteBufUtil.readQ(byteBuf);
    }

    public boolean isValidFlag() {
        return flag == 0;
    }
}
