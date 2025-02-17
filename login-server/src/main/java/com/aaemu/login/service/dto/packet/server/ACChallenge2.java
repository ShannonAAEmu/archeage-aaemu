package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ACChallenge2 {
    private int round;
    private String salt;
    private List<Integer> ch;

    public ACChallenge2() {
        this.ch = new ArrayList<>(8);
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(40 + salt.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.AC_CHALLENGE_2, byteBuf);
        byteBufUtil.writeInt(round, byteBuf);
        byteBufUtil.writeString(salt, byteBuf);
        for (Integer i : ch) {
            byteBufUtil.writeInt(i, byteBuf);
        }
        return byteBuf;
    }
}
