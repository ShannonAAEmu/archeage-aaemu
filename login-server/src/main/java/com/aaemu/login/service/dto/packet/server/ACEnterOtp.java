package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACEnterOtp {
    private int mt; // max try
    private int ct; // current try

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACEnterOtp, byteBuf);
        byteBufUtil.writeD(mt, byteBuf);
        byteBufUtil.writeD(ct, byteBuf);
        return byteBuf;
    }
}
