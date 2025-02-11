package com.aaemu.zone.service.dto.packet.server;

import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class WZJoinResponsePacket {
    private byte reason;
    private String fSet;
    private boolean bf;

    public void setReason(int reason) {
        this.reason = (byte) reason;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeOpcode(ServerPacket.WZ_Join_Response, byteBuf);
        byteBufUtils.writeW(reason, byteBuf);
        byteBufUtils.writeS(fSet, byteBuf);
        byteBufUtils.writeBoolean(bf, byteBuf);
        return byteBuf;
    }
}
