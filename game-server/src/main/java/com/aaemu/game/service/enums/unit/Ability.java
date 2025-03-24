package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum Ability {
    GENERAL(0),
    FIGHT(1),
    ILLUSION(2),
    ADAMANT(3),
    WILL(4),
    DEATH(5),
    WILD(6),
    MAGIC(7),
    VOCATION(8),
    ROMANCE(9),
    LOVE(10),
    NONE(11);

    private final int ability;

    public static Ability getById(int type) {
        for (Ability ability : values()) {
            if (ability.getAbility() == type) {
                return ability;
            }
        }
        throw new GameServerException("Unknown ability type: " + type);
    }

    public static Ability getById(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        return getById(byteBufUtil.readByte(byteBuf));
    }
}
