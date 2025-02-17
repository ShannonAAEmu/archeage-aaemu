package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.client.Character;
import com.aaemu.login.service.dto.client.ServerInfo;
import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.model.ServerRaceCongestion;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class ACWorldList {
    private final ServerInfo serverInfo;
    private byte count;
    private byte characterCount;   // chCount
    private List<Character> characterList;

    public ACWorldList(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        this.count = (byte) (Objects.isNull(serverInfo) ? 0 : 1);
        this.characterList = serverInfo.getCharacters();
        this.characterCount = (byte) serverInfo.getCharacters().size();
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.AC_WORLD_LIST, byteBuf);
        byteBufUtil.writeByte(count, byteBuf);
        byteBufUtil.writeByte(serverInfo.getId(), byteBuf);
        byteBufUtil.writeString(serverInfo.getName(), byteBuf);
        byteBufUtil.writeBoolean(serverInfo.isAvailable().getStatus(), byteBuf);
        if (serverInfo.isAvailable().getStatus()) {
            byteBufUtil.writeByte(serverInfo.getCon().getCongestion(), byteBuf);
            ServerRaceCongestion rCon = serverInfo.getRCon();
            byteBufUtil.writeBoolean(rCon.isWarborn(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isNuian(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isReturned(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isFairy(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isElf(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isHariharan(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isFerre(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isDwarf(), byteBuf);
            byteBufUtil.writeBoolean(rCon.isNone(), byteBuf);
        }
        byteBufUtil.writeByte(characterCount, byteBuf);
        characterList.forEach(character -> {
            byteBufUtil.writeInt(character.getAccountId(), byteBuf);
            byteBufUtil.writeByte(character.getWorldId(), byteBuf);
            byteBufUtil.writeInt(character.getCharId(), byteBuf);
            byteBufUtil.writeString(character.getName(), byteBuf);
            byteBufUtil.writeByte(character.getCharRace(), byteBuf);
            byteBufUtil.writeByte(character.getCharGender(), byteBuf);
            byteBufUtil.writeString(character.getGuid(), byteBuf);
            byteBufUtil.write(character.getV(), byteBuf);
        });
        return byteBuf;
    }
}
