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
public class ACJoinResponse extends Packet {
    private int reason;

    /**
     * afs[0] -> max number of characters per account
     * afs[1] -> additional number of characters per server when using the slot increase item
     * afs[2] -> 1 - character pre-creation mode 1-режим предварительного создания персонажей
     */
    private long afs;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACJoinResponse.getOpcode(), byteBuf);
        byteBufUtil.writeW(reason, byteBuf);
        byteBufUtil.writeQ(afs, byteBuf);
        return byteBuf;
    }
}
