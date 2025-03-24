package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class BodyParts {
    private int face;
    private int hair;
    private int eyeLeft;
    private int eyeRight;
    private int tail;
    private int body;
    private int beard;

    public BodyParts() {
    }

    public BodyParts(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.face = byteBufUtil.readInt(byteBuf);
        this.hair = byteBufUtil.readInt(byteBuf);
        this.eyeLeft = byteBufUtil.readInt(byteBuf);
        this.eyeRight = byteBufUtil.readInt(byteBuf);
        this.tail = byteBufUtil.readInt(byteBuf);
        this.body = byteBufUtil.readInt(byteBuf);
        this.beard = byteBufUtil.readInt(byteBuf);
    }
}
