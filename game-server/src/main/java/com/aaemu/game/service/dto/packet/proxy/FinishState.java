package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class FinishState implements ClientPacket {

    private int state;

    public FinishState(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.state = (int) byteBufUtil.readD(byteBuf);
    }
}
