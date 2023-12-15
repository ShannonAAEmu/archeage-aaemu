package com.aaemu.stream.service.dto.packet.server;

import com.aaemu.stream.service.enums.ServerPacket;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class TCJoinResponse {
    private byte response;

    public void setResponse(int response) {
        this.response = (byte) response;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.TC_JOIN_RESPONSE, byteBuf);
        byteBufUtil.writeB(response, byteBuf);
        return byteBuf;
    }
}
