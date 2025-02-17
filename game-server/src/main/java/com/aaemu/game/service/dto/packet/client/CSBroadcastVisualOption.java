package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.util.ByteBufUtil;
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
    private final byte vOptFlag;     // voptflag
    private List<Byte> stpList;     // stp
    private boolean helmet;

    public CSBroadcastVisualOption(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.channel = channel;
        this.vOptFlag = byteBufUtil.readByte(byteBuf);
        if (this.vOptFlag == 1) {
            this.stpList = new ArrayList<>(6);
            for (int i = 0; i < 6; i++) {
                this.stpList.add(byteBufUtil.readByte(byteBuf));
            }
        } else if (this.vOptFlag == 2) {
            this.helmet = byteBufUtil.readBoolean(byteBuf);
        }
    }
}
