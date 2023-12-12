package com.aaemu.game.service.dto.packet.proxy;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FinishState extends Packet {

    private int state;

    public FinishState(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.state = (int) byteBufUtil.readD(byteBuf);
    }
}
