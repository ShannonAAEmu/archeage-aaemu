package com.aaemu.game.service.model.unit;

import com.aaemu.game.service.enums.unit.ExtendedModelType;
import com.aaemu.game.service.model.face.Face;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class HeadDetails {
    private final ByteBufUtil byteBufUtil;
    private final ExtendedModelType customModelType;  // ext
    private int hairColorId;    // type
    private int skinColorId;    // type
    private Face face;

    public HeadDetails(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.customModelType = ExtendedModelType.get(byteBufUtil.readByte(byteBuf));
        this.hairColorId = byteBufUtil.readInt(byteBuf);
        this.skinColorId = byteBufUtil.readInt(byteBuf);
        if (ExtendedModelType.HEAD.equals(customModelType)) {
            this.face = new Face(byteBufUtil, byteBuf);
        }
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeByte(customModelType.getType(), byteBuf);
        byteBufUtil.writeInt(hairColorId, byteBuf);
        byteBufUtil.writeInt(skinColorId, byteBuf);
        if (ExtendedModelType.HEAD.equals(customModelType)) {
            byteBufUtil.write(face.build(), byteBuf);
        }
        return byteBuf;
    }
}
