package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.dto.client.AccountFutureSet;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

@Data
public class ACJoinResponse {
    private short reason;
    private AccountFutureSet accountFutureSet;   // afs

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(12);
        byteBufUtil.writeOpcode(ServerPacket.AC_JOIN_RESPONSE, byteBuf);
        byteBufUtil.writeShort(reason, byteBuf);
        byteBufUtil.writeByte(accountFutureSet.getTotalCharactersLimit(), byteBuf);
        byteBufUtil.writeBoolean(accountFutureSet.isPreSelectCharacterPeriod(), byteBuf);
        byteBufUtil.write(accountFutureSet.getUnknown(), byteBuf);
        return byteBuf;
    }
}
