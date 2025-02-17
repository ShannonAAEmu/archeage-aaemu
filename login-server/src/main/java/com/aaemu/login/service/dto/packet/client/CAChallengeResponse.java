package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CAChallengeResponse implements ClientPacket {
    private final Channel channel;
    private final List<Integer> ch;
    private final String pw;

    public CAChallengeResponse(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.ch = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.ch.add(byteBufUtil.readInt(byteBuf));
        }
        this.pw = byteBufUtil.readStringAsHex(byteBuf);
    }
}
