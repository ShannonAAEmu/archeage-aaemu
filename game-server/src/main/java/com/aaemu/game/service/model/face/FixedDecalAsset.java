package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public abstract class FixedDecalAsset {
    private final ByteBufUtil byteBufUtil;
    private final int id;       // type
    private final float weight;

    public FixedDecalAsset(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.id = byteBufUtil.readInt(byteBuf);
        this.weight = byteBufUtil.readFloat(byteBuf);
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeInt(id, byteBuf);
        byteBufUtil.writeFloat(weight, byteBuf);
        return byteBuf;
    }
}
