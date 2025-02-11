package com.aaemu.editor.service.dto.packet.server;

import com.aaemu.editor.service.enums.ServerPacket;
import com.aaemu.editor.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ECLoginResponse {
    /**
     * 0 = "success"
     * 1 = "failed"
     * 2 = "Protocol Mismatch! Update SVN First!"
     * else = "unknown"
     */
    private byte reason;
    private String fileServerPath;

    public void setReason(int reason) {
        this.reason = (byte) reason;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer(5 + fileServerPath.getBytes().length);
        byteBufUtils.writeOpcode(ServerPacket.EC_LOGIN_RESPONSE, byteBuf);
        byteBufUtils.writeB(reason, byteBuf);
        byteBufUtils.writeS(fileServerPath, byteBuf);
        return byteBuf;
    }
}
