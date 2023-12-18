package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.model.Character;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
public class SCCharacterList {
    private final byte last;
    private byte count;

    @Setter
    private List<Character> characterList;

    public SCCharacterList() {
        this.last = 1;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        count = (byte) characterList.size();
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
