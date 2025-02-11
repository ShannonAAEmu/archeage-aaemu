package com.aaemu.game.service.model.unit;

import com.aaemu.game.service.enums.unit.UnitCustomModelType;
import com.aaemu.game.service.model.face.Face;
import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class HeadDetails {
    private final UnitCustomModelType customModelType;  // ext
    private Face face;

    public HeadDetails(ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.customModelType = UnitCustomModelType.get(byteBufUtil.readB(byteBuf));
        if (UnitCustomModelType.HEAD.equals(customModelType)) {
            this.face = new Face(byteBufUtil, byteBuf);
        }
    }
}
