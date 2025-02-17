package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum AbilityType {
    GENERAL((byte) 0),
    FIGHT((byte) 1),
    ILLUSION((byte) 2),
    ADAMANT((byte) 3),
    WILL((byte) 4),
    DEATH((byte) 5),
    WILD((byte) 6),
    MAGIC((byte) 7),
    VOCATION((byte) 8),
    ROMANCE((byte) 9),
    LOVE((byte) 10),
    NONE((byte) 11);

    private final byte type;

    public static AbilityType get(byte type) {
        for (AbilityType abilityType : values()) {
            if (abilityType.getType() == type) {
                return abilityType;
            }
        }
        throw new GameServerException("Unknown ability type: " + type);
    }
}
