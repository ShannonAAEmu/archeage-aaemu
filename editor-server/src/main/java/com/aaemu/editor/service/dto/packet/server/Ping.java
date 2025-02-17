package com.aaemu.editor.service.dto.packet.server;

import com.aaemu.editor.service.enums.ServerPacket;
import com.aaemu.editor.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Ping {

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(2);
        byteBufUtil.writeOpcode(ServerPacket.EC_PING, byteBuf);
        return byteBuf;
    }
}
