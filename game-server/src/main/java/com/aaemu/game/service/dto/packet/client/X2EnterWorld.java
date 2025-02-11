package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class X2EnterWorld implements ClientPacket {
    private final Channel channel;
    private final long p_from;
    private final long p_to;
    private final long accountId;
    private final long cookie;
    private final long zoneId;
    private final byte tb;

    public X2EnterWorld(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
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
