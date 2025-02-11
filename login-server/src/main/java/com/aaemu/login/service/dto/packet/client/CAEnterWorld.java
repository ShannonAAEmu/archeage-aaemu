package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CAEnterWorld implements ClientPacket {
    private final Channel channel;
    private final long flag;
    private final int wid;   // World id

    public CAEnterWorld(Channel channel, ByteBufUtils byteBufUtils, ByteBuf byteBuf, boolean isEditorMode) {
        this.channel = channel;
        if (isEditorMode) {
            this.wid = byteBufUtils.readB(byteBuf);
            this.flag = byteBufUtils.readQ(byteBuf);
        } else {
            this.flag = byteBufUtils.readQ(byteBuf);
            this.wid = byteBufUtils.readB(byteBuf);
        }
    }
}
