package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACJoinResponse {
    private int reason;

    /**
     * Account future set
     * <p>
     * afs[0] -> max number of characters per account
     * afs[1] -> additional number of characters per server when using the slot increase item
     * afs[2] -> 1 - character pre-creation mode 1-режим предварительного создания персонажей
     */
    private long afs;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACJoinResponse, byteBuf);
        byteBufUtil.writeW(reason, byteBuf);
        byteBufUtil.writeQ(afs, byteBuf);
        return byteBuf;
    }
}
