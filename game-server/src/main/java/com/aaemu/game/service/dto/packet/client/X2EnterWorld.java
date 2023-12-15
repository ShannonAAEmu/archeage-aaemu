package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class X2EnterWorld implements ClientPacket {
    private final long p_from;
    private final long p_to;
    private final long accountId;
    private final long cookie;
    private final long zoneId;
    private final int tb;

    public X2EnterWorld(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.p_from = byteBufUtil.readD(byteBuf);
        this.p_to = byteBufUtil.readD(byteBuf);
        this.accountId = byteBufUtil.readD(byteBuf);
        this.cookie = byteBufUtil.readD(byteBuf);
        String zoneId = byteBufUtil.readS(4, byteBuf);
        if (StringUtils.isNumeric(zoneId)) {
            this.zoneId = Integer.parseInt(zoneId);
        } else {
            this.zoneId = 0;
        }
        this.tb = byteBufUtil.readB(byteBuf);
    }
}
