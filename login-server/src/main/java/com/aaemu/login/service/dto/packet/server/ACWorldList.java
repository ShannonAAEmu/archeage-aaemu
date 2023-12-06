package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.entity.Character;
import com.aaemu.login.service.entity.Server;
import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.service.dto.packet.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ACWorldList extends Packet {
    private byte count;
    private List<Server> serverList;
    private byte chCount;
    private List<Character> characterList;

    public void setCount(int count) {
        this.count = (byte) count;
    }

    public void setChCount(int chCount) {
        this.chCount = (byte) chCount;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACWorldList.getOpcode(), byteBuf);
        byteBufUtil.writeB(count, byteBuf);
        serverList.forEach(server -> {
            byteBufUtil.writeB(server.getId(), byteBuf);
            byteBufUtil.writeS(server.getName(), byteBuf);
            byteBufUtil.writeBoolean(server.isAvailable(), byteBuf);
            if (server.isAvailable()) {
                byteBufUtil.writeB(server.getCon(), byteBuf);
                server.getRCon().forEach(rCon -> byteBufUtil.writeBoolean(rCon, byteBuf));
            }
        });
        byteBufUtil.writeB(chCount, byteBuf);
        characterList.forEach(character -> {
            byteBufUtil.writeD(character.getAccountId(), byteBuf);
            byteBufUtil.writeB(character.getWorldId(), byteBuf);
            byteBufUtil.writeD(character.getCharId(), byteBuf);
            byteBufUtil.writeS(character.getName(), byteBuf);
            byteBufUtil.writeB(character.getCharRace(), byteBuf);
            byteBufUtil.writeB(character.getCharGender(), byteBuf);
            byteBufUtil.writeS(character.getGuid(), byteBuf);
            byteBufUtil.writeQ(character.getV(), byteBuf);
        });
        return byteBuf;
    }
}
