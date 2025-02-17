package com.aaemu.editor.service.dto.packet.server;

import com.aaemu.editor.service.enums.ServerPacket;
import com.aaemu.editor.service.util.ByteBufUtil;
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

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(5 + fileServerPath.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.EC_LOGIN_RESPONSE, byteBuf);
        byteBufUtil.writeByte(reason, byteBuf);
        byteBufUtil.writeString(fileServerPath, byteBuf);
        return byteBuf;
    }
}
