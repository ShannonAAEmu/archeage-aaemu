package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shannon
 */
@Data
public class CSBroadcastVisualOption implements ClientPacket {
    private final Channel channel;
    private final byte voptFlag;     // voptflag
    private List<Byte> stpList;     // stp
    private boolean helmet;

    public CSBroadcastVisualOption(Channel channel, ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.voptFlag = byteBufUtil.readB(byteBuf);
        if (this.voptFlag == 1) {
            this.stpList = new ArrayList<>(6);
            for (int i = 0; i < 6; i++) {
                this.stpList.add(byteBufUtil.readB(byteBuf));
            }
        } else if (this.voptFlag == 2) {
            this.helmet = byteBufUtil.readBoolean(byteBuf);
        }
    }
}
