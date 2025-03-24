package com.aaemu.zone.service.dto.packet.server;

import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class WZRunCommand {
    private int id;
    private String command; // cmd

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.WZ_RUN_COMMAND, byteBuf);
        byteBufUtil.writeInt(id, byteBuf);
        byteBufUtil.writeString(command, byteBuf);
        return byteBuf;
    }
}
