package com.aaemu.zone.service.dto.packet.server;

import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class WZSpawnerList {
    private boolean last;
    private byte count;

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeOpcode(ServerPacket.WZ_SPAWNER_LIST, byteBuf);
        byteBufUtil.writeBoolean(last, byteBuf);
        byteBufUtil.writeByte(count, byteBuf);
        if (count > 0) {
            // write int: id
            // write int: type
            // write boolean: state
        }
        return byteBuf;
    }
}
