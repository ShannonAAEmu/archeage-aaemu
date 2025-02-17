package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.model.FutureSet;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class SCInitialConfig {
    private String host;
    private FutureSet futureSet;
    private int count;  // unknown game\scripts\x2ui\baselib\candidatlist.lua
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

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_INITIAL_CONFIG, byteBuf);
        byteBufUtil.writeString(host, byteBuf);
        byteBufUtil.writeString(futureSet.build(), byteBuf);
        byteBufUtil.writeInt(count, byteBuf);
        if (count > 0) {
            byteBufUtil.writeInt(0, byteBuf);
        }
        byteBufUtil.writeByte(searchLevel, byteBuf);
        byteBufUtil.writeByte(bidLevel, byteBuf);
        byteBufUtil.writeByte(postLevel, byteBuf);
        return byteBuf;
    }
}
