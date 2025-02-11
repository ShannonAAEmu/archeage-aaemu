package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACJoinResponse {
    private int reason;

    /**
     * afs[0] -> Is available enter to world
     * afs[0] -> ? (max number of characters per account)
     * afs[1] -> ? (additional number of characters per server when using the slot increase item)
     * afs[2] -> ? (1 - character pre-creation mode)
     */
    private long afs;   // Account future set

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(12);
        byteBufUtils.writeOpcode(ServerPacket.ACJoinResponse, byteBuf);
        byteBufUtils.writeW(reason, byteBuf);
        byteBufUtils.writeQ(afs, byteBuf);
        return byteBuf;
    }
}
