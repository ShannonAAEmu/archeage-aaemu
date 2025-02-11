package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACEnterOtp {
    private final int mt; // max try
    private final int ct; // current try

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        byteBufUtils.writeOpcode(ServerPacket.ACEnterOtp, byteBuf);
        byteBufUtils.writeD(mt, byteBuf);
        byteBufUtils.writeD(ct, byteBuf);
        return byteBuf;
    }
}
