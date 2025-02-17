package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtil;
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
public class Brows extends FixedDecalAsset {

    public Brows(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        super(byteBufUtil, byteBuf);
    }
}
