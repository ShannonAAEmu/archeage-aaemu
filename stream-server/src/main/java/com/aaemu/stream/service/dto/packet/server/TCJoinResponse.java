package com.aaemu.stream.service.dto.packet.server;

import com.aaemu.stream.service.enums.ServerPacket;
import com.aaemu.stream.service.util.ByteBufUtils;
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

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(3);
        byteBufUtils.writeOpcode(ServerPacket.TC_JOIN_RESPONSE, byteBuf);
        byteBufUtils.writeB(response, byteBuf);
        return byteBuf;
    }
}
