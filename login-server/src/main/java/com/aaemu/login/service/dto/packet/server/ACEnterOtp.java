package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ACEnterOtp {
    private final int mt; // max try
    private final int ct; // current try

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        byteBufUtil.writeOpcode(ServerPacket.AC_ENTER_OTP, byteBuf);
        byteBufUtil.writeInt(mt, byteBuf);
        byteBufUtil.writeInt(ct, byteBuf);
        return byteBuf;
    }
}
