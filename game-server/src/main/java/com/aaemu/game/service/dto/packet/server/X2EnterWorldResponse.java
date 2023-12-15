package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

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

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.X2_ENTER_WORLD_RESPONSE, byteBuf);
        byteBufUtil.writeW(reason, byteBuf);
        byteBufUtil.writeB(gm, byteBuf);
        byteBufUtil.writeD(sc, byteBuf);
        byteBufUtil.writeW(sp, byteBuf);
        byteBufUtil.writeQ(wf, byteBuf);
        return byteBuf;
    }
}
