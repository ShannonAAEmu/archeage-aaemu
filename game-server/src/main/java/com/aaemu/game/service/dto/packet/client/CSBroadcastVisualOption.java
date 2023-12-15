package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CSBroadcastVisualOption implements ClientPacket {
    private final int voptFlag;     // voptflag
    private List<Integer> stpList;  // stp
    private boolean helmet;

    public CSBroadcastVisualOption(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
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
