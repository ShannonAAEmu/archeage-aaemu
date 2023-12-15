package com.aaemu.stream.service.dto.packet.client;

import com.aaemu.stream.service.dto.packet.ClientPacket;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CTJoin implements ClientPacket {
    private final long accountId;
    private final long cookie;

    public CTJoin(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.accountId = byteBufUtil.readD(byteBuf);
        this.cookie = byteBufUtil.readD(byteBuf);
    }
}
