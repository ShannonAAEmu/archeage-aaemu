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
public class Tattoo extends FixedDecalAsset {

    public Tattoo(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        super(byteBufUtil, byteBuf);
    }
}
