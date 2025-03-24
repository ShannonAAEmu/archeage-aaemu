package com.aaemu.zone.service.dto.packet.server;

import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.model.FutureSet;
import com.aaemu.zone.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class WZJoinResponse {
    private byte reason;
    private FutureSet futureSet; // fSet
    private boolean bf;

    public void setReason(int reason) {
        this.reason = (byte) reason;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.WZ_Join_Response, byteBuf);
        byteBufUtil.writeShort(reason, byteBuf);
        byteBufUtil.writeString(futureSet.build(), byteBuf);
        byteBufUtil.writeBoolean(bf, byteBuf);
        return byteBuf;
    }
}
