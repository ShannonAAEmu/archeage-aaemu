package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.model.Character;
import com.aaemu.game.service.util.ByteBufUtil;
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
    private boolean isLast; // last
    private byte count;

    @Setter
    private List<Character> characterList;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        count = (byte) characterList.size();
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CHARACTER_LIST, byteBuf);
        byteBufUtil.writeBoolean(isLast, byteBuf);
        byteBufUtil.writeByte(count, byteBuf);
        if (count > 0) {
            for (Character character : characterList) {
                // TODO character info
            }
        }
        return byteBuf;
    }
}
