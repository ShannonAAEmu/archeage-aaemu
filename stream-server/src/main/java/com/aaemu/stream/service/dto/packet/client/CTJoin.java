package com.aaemu.stream.service.dto.packet.client;

import com.aaemu.stream.service.dto.packet.Packet;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CTJoin extends Packet {
    private final long accountId;
    private final long cookie;

    public CTJoin(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.accountId = byteBufUtil.readD(byteBuf);
        this.cookie = byteBufUtil.readD(byteBuf);
    }
}
