package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CAListWorld implements ClientPacket {
    private final long flag;

    public CAListWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.flag = byteBufUtil.readQ(byteBuf);
    }
}
