package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ACChallenge {
    private int salt;
    private List<Integer> ch;

    public ACChallenge() {
        this.ch = new ArrayList<>(4);
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(22);
        byteBufUtil.writeOpcode(ServerPacket.AC_CHALLENGE, byteBuf);
        byteBufUtil.writeInt(salt, byteBuf);
        for (Integer i : ch) {
            byteBufUtil.writeInt(i, byteBuf);
        }
        return byteBuf;
    }
}
