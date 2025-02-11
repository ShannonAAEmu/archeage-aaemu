package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtils;
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
    private final ByteBufUtils byteBufUtil;
    private final long movableDecalAssetId;
    private final float movableDecalWeight;
    private final float movableDecalScale;
    private final float movableDecalRotate;
    private final int movableDecalMoveX;
    private final int movableDecalMoveY;

    public MovableDecal(ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.movableDecalAssetId = byteBufUtil.readD(byteBuf);
        this.movableDecalWeight = byteBufUtil.readF(byteBuf);
        this.movableDecalScale = byteBufUtil.readF(byteBuf);
        this.movableDecalRotate = byteBufUtil.readF(byteBuf);
        this.movableDecalMoveX = byteBufUtil.readW(byteBuf);
        this.movableDecalMoveY = byteBufUtil.readW(byteBuf);
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeD((int) movableDecalAssetId, byteBuf);
        byteBufUtil.writeF((int) movableDecalWeight, byteBuf);
        byteBufUtil.writeF((int) movableDecalScale, byteBuf);
        byteBufUtil.writeF((int) movableDecalRotate, byteBuf);
        byteBufUtil.writeW(movableDecalMoveX, byteBuf);
        byteBufUtil.writeW(movableDecalMoveY, byteBuf);
        return byteBuf;
    }
}
