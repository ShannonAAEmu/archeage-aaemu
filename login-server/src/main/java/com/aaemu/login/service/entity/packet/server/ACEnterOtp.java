package com.aaemu.login.service.entity.packet.server;

import com.aaemu.login.service.entity.packet.Packet;
import com.aaemu.login.service.entity.packet.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ACEnterOtp extends Packet {
    private int mt; // max try
    private int ct; // current try

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACEnterOtp.getOpcode(), byteBuf);
        byteBufUtil.writeD(mt, byteBuf);
        byteBufUtil.writeD(ct, byteBuf);
        return byteBuf;
    }
}
