package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.service.dto.packet.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SCInitialConfig extends Packet {
    private String host;
    private String fSet;
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

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_INITIAL_CONFIG, byteBuf);
        byteBufUtil.writeS(host, byteBuf);
        byteBufUtil.writeS(fSet, byteBuf);
        byteBufUtil.writeD(count, byteBuf);
        byteBufUtil.writeB(searchLevel, byteBuf);
        byteBufUtil.writeB(bidLevel, byteBuf);
        byteBufUtil.writeB(postLevel, byteBuf);
        return byteBuf;
    }
}
