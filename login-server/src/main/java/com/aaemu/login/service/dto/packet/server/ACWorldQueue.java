package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.service.dto.packet.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ACWorldQueue extends Packet {
    private byte wid;
    private int turnCount;
    private int totalCount;

    public void setWid(int wid) {
        this.wid = (byte) wid;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACWorldQueue, byteBuf);
        byteBufUtil.writeB(wid, byteBuf);
        byteBufUtil.writeW(turnCount, byteBuf);
        byteBufUtil.writeW(totalCount, byteBuf);
        return byteBuf;
    }
}
