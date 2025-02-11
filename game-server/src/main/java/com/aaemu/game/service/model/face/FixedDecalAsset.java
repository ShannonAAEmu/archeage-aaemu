package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public abstract class FixedDecalAsset {
    private final ByteBufUtils byteBufUtil;
    private final long id;
    private final float weight;

    public FixedDecalAsset(ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.id = byteBufUtil.readD(byteBuf);
        this.weight = byteBufUtil.readF(byteBuf);
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeD((int) id, byteBuf);
        byteBufUtil.writeF(weight, byteBuf);
        return byteBuf;
    }
}
