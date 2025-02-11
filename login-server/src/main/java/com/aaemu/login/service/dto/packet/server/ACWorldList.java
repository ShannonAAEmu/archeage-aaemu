package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.client.CharacterDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.model.ServerRaceCongestion;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;

@Data
public class ACWorldList {
    private byte count;
    private ServerDto serverDto;
    private byte chCount;   // character count
    private List<CharacterDto> characterDtoList;

    public void setCount(int count) {
        this.count = (byte) count;
    }

    public void setChCount(int chCount) {
        this.chCount = (byte) chCount;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();    // TODO calc size
        byteBufUtils.writeOpcode(ServerPacket.ACWorldList, byteBuf);
        byteBufUtils.writeB(count, byteBuf);
        byteBufUtils.writeB(serverDto.getId(), byteBuf);
        byteBufUtils.writeS(serverDto.getName(), byteBuf);
        byteBufUtils.writeBoolean(serverDto.isAvailable().getStatus(), byteBuf);
        if (serverDto.isAvailable().getStatus()) {
            byteBufUtils.writeB(serverDto.getCon().getCongestion(), byteBuf);
            ServerRaceCongestion rCon = serverDto.getRCon();
            byteBufUtils.writeBoolean(rCon.isWarborn(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isNuian(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isReturned(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isFairy(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isElf(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isHariharan(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isFerre(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isDwarf(), byteBuf);
            byteBufUtils.writeBoolean(rCon.isNone(), byteBuf);
        }
        byteBufUtils.writeB(chCount, byteBuf);
        characterDtoList.forEach(characterDto -> {
            byteBufUtils.writeD(characterDto.getAccountId(), byteBuf);
            byteBufUtils.writeB(characterDto.getWorldId(), byteBuf);
            byteBufUtils.writeD(characterDto.getCharId(), byteBuf);
            byteBufUtils.writeS(characterDto.getName(), byteBuf);
            byteBufUtils.writeB(characterDto.getCharRace(), byteBuf);
            byteBufUtils.writeB(characterDto.getCharGender(), byteBuf);
            byteBufUtils.writeS(characterDto.getGuid(), byteBuf);
            byteBufUtils.writeQ(characterDto.getV(), byteBuf);
        });
        return byteBuf;
    }
}
