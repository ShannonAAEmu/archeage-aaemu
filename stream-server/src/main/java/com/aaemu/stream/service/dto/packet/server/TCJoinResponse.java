package com.aaemu.stream.service.dto.packet.server;

import com.aaemu.stream.service.enums.ServerPacket;
import com.aaemu.stream.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class TCJoinResponse {
    private byte response;

    public TCJoinResponse(byte response) {
        this.response = response;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(3);
        byteBufUtil.writeOpcode(ServerPacket.TC_JOIN_RESPONSE, byteBuf);
        byteBufUtil.writeByte(response, byteBuf);
        return byteBuf;
    }
}
