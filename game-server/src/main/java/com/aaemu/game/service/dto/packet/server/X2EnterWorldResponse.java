package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class X2EnterWorldResponse {
    private byte reason;
    private byte gm;
    private int sc;
    private int sp;
    private long wf;

    public void setReason(int reason) {
        this.reason = (byte) reason;
    }

    public void setGm(int gm) {
        this.gm = (byte) gm;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(21);
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.X2_ENTER_WORLD_RESPONSE, byteBuf);
        byteBufUtils.writeW(reason, byteBuf);
        byteBufUtils.writeB(gm, byteBuf);
        byteBufUtils.writeD(sc, byteBuf);
        byteBufUtils.writeW(sp, byteBuf);
        byteBufUtils.writeQ(wf, byteBuf);
        return byteBuf;
    }
}
