package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@Data
@RequiredArgsConstructor
public abstract class MovableDecal {
    private final ByteBufUtil byteBufUtil;
    private final int movableDecalAssetId;  // type
    private final float movableDecalWeight; // weight
    private final float movableDecalScale;  // scale
    private final float movableDecalRotate; // rotate
    private final short movableDecalMoveX;  // moveX
    private final short movableDecalMoveY;  // moveY

    public MovableDecal(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.movableDecalAssetId = byteBufUtil.readInt(byteBuf);
        this.movableDecalWeight = byteBufUtil.readFloat(byteBuf);
        this.movableDecalScale = byteBufUtil.readFloat(byteBuf);
        this.movableDecalRotate = byteBufUtil.readFloat(byteBuf);
        this.movableDecalMoveX = byteBufUtil.readShort(byteBuf);
        this.movableDecalMoveY = byteBufUtil.readShort(byteBuf);
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeInt(movableDecalAssetId, byteBuf);
        byteBufUtil.writeFloat(movableDecalWeight, byteBuf);
        byteBufUtil.writeFloat(movableDecalScale, byteBuf);
        byteBufUtil.writeFloat(movableDecalRotate, byteBuf);
        byteBufUtil.writeShort(movableDecalMoveX, byteBuf);
        byteBufUtil.writeShort(movableDecalMoveY, byteBuf);
        return byteBuf;
    }
}
