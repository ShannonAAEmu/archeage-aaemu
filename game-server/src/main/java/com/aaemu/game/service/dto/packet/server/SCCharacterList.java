package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.model.Character;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.Setter;

import java.util.List;

/**
 * @author Shannon
 */
@Data
public class SCCharacterList {
    private final byte last;
    private byte count;

    @Setter
    private List<Character> characterList;

    public SCCharacterList() {
        this.last = 1;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        count = (byte) characterList.size();
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_CHARACTER_LIST, byteBuf);
        byteBufUtils.writeB(last, byteBuf);
        byteBufUtils.writeB(count, byteBuf);
        if (count > 0) {
            for (Character character : characterList) {
                // TODO character info
            }
        }
        return byteBuf;
    }
}
