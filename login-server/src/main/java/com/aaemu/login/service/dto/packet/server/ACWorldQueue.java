package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACWorldQueue {
    private byte wid;
    private int turnCount;
    private int totalCount;

    public void setWid(int wid) {
        this.wid = (byte) wid;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(7);
        byteBufUtils.writeOpcode(ServerPacket.ACWorldQueue, byteBuf);
        byteBufUtils.writeB(wid, byteBuf);
        byteBufUtils.writeW(turnCount, byteBuf);
        byteBufUtils.writeW(totalCount, byteBuf);
        return byteBuf;
    }
}
