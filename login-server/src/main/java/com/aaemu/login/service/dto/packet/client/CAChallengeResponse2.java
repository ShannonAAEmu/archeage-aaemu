package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

@Data
public class CAChallengeResponse2 implements ClientPacket {
    private long ch;

    public CAChallengeResponse2(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        for (int i = 0; i < 8; i++) {
            this.ch = byteBufUtil.readD(byteBuf);
        }
    }
}
