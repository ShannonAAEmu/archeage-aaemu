package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CAChallengeResponse extends Packet {
    private long ch;
    private final String pw;

    public CAChallengeResponse(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        for (int i = 0; i < 4; i++) {
            this.ch = byteBufUtil.readD(byteBuf);
        }
        this.pw = byteBufUtil.readString(byteBuf);
    }
}
