package com.aaemu.login.service.dto.packet.client;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.Data;

@Data
public class CAEnterWorld implements ClientPacket {
    private final Channel channel;
    private final long flag;
    private final byte wid;   // World id

    public CAEnterWorld(Channel channel, ByteBufUtil byteBufUtil, ByteBuf byteBuf, boolean isEditorMode) {
        this.channel = channel;
        if (isEditorMode) {
            this.wid = byteBufUtil.readByte(byteBuf);
            this.flag = byteBufUtil.readLong(byteBuf);
        } else {
            this.flag = byteBufUtil.readLong(byteBuf);
            this.wid = byteBufUtil.readByte(byteBuf);
        }
    }
}
