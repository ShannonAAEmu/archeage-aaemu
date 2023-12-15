package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.client.CharacterDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.util.List;

@Data
public class ACWorldList {
    private byte count;
    private List<ServerDto> serverDtoList;
    private byte chCount;
    private List<CharacterDto> characterDtoList;

    public void setCount(int count) {
        this.count = (byte) count;
    }

    public void setChCount(int chCount) {
        this.chCount = (byte) chCount;
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.ACWorldList, byteBuf);
        byteBufUtil.writeB(count, byteBuf);
        serverDtoList.forEach(serverDto -> {
            byteBufUtil.writeB(serverDto.getId(), byteBuf);
            byteBufUtil.writeS(serverDto.getName(), byteBuf);
            byteBufUtil.writeBoolean(serverDto.isAvailable(), byteBuf);
            if (serverDto.isAvailable()) {
                byteBufUtil.writeB(serverDto.getCon(), byteBuf);
                serverDto.getRCon().forEach(rCon -> byteBufUtil.writeBoolean(rCon, byteBuf));
            }
        });
        byteBufUtil.writeB(chCount, byteBuf);
        characterDtoList.forEach(characterDto -> {
            byteBufUtil.writeD(characterDto.getAccountId(), byteBuf);
            byteBufUtil.writeB(characterDto.getWorldId(), byteBuf);
            byteBufUtil.writeD(characterDto.getCharId(), byteBuf);
            byteBufUtil.writeS(characterDto.getName(), byteBuf);
            byteBufUtil.writeB(characterDto.getCharRace(), byteBuf);
            byteBufUtil.writeB(characterDto.getCharGender(), byteBuf);
            byteBufUtil.writeS(characterDto.getGuid(), byteBuf);
            byteBufUtil.writeQ(characterDto.getV(), byteBuf);
        });
        return byteBuf;
    }
}
