package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class X2EnterWorldResponse {
    private short reason;
    private byte gameMode;    // gm
    private int streamServerCookie; // sc
    private int streamServerPort; // sp
    private long wf;

    public void setReason(int reason) {
        this.reason = (short) reason;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = (byte) gameMode;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(21);
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.X2_ENTER_WORLD_RESPONSE, byteBuf);
        byteBufUtil.writeShort(reason, byteBuf);
        byteBufUtil.writeByte(gameMode, byteBuf);
        byteBufUtil.writeInt(streamServerCookie, byteBuf);
        byteBufUtil.writeShort(streamServerPort, byteBuf);
        byteBufUtil.writeLong(wf, byteBuf);
        return byteBuf;
    }
}
