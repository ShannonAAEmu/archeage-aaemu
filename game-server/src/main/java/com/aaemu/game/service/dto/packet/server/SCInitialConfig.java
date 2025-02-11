package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCInitialConfig {
    private String host;
    private String fSet;    // siege
    private int count;
    private byte searchLevel;
    private byte bidLevel;
    private byte postLevel;

    public void setSearchLevel(int searchLevel) {
        this.searchLevel = (byte) searchLevel;
    }

    public void setBidLevel(int bidLevel) {
        this.bidLevel = (byte) bidLevel;
    }

    public void setPostLevel(int postLevel) {
        this.postLevel = (byte) postLevel;
    }

    public ByteBuf build(ByteBufUtils byteBufUtils) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtils.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtils.writeOpcode(ServerPacket.SC_INITIAL_CONFIG, byteBuf);
        byteBufUtils.writeS(host, byteBuf);
        byteBufUtils.writeS(fSet, byteBuf);
        byteBufUtils.writeD(count, byteBuf);
        if (count > 0) {
            byteBufUtils.writeD(0, byteBuf);    // unknown
        }
        byteBufUtils.writeB(searchLevel, byteBuf);
        byteBufUtils.writeB(bidLevel, byteBuf);
        byteBufUtils.writeB(postLevel, byteBuf);
        return byteBuf;
    }
}
