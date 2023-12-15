package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.model.Character;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;

@Data
public class SCCharacterList {
    private byte last;
    private byte count;
    private List<Character> characterList;

    public void setLast(int last) {
        this.last = (byte) last;
    }

    public void setCount(int count) {
        this.count = (byte) count;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CHARACTER_LIST, byteBuf);
        byteBufUtil.writeB(last, byteBuf);
        byteBufUtil.writeB(count, byteBuf);
        if (count > 0) {
            for (Character character : characterList) {
                // TODO character info
            }
        }
        return byteBuf;
    }
}
