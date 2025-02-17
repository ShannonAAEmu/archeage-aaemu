package com.aaemu.login.service.dto.packet.server;

import com.aaemu.login.service.dto.client.QueueStatus;
import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
@AllArgsConstructor
public class ACWorldCookie {
    private int cookie;
    private String ip;
    private int port;

    public ACWorldCookie(int cookie, QueueStatus queueStatus) {
        this.cookie = cookie;
        this.ip = queueStatus.getIp();
        this.port = queueStatus.getPort();
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer(10 + ip.getBytes().length);
        byteBufUtil.writeOpcode(ServerPacket.AC_WORLD_COOKIE, byteBuf);
        byteBufUtil.writeInt(cookie, byteBuf);
        byteBufUtil.writeString(ip, byteBuf);
        byteBufUtil.writeShort(port, byteBuf);
        return byteBuf;
    }
}
