package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Shannon
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EyeLining extends FixedDecalAsset {

    public EyeLining(ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        super(byteBufUtil, byteBuf);
    }
}
