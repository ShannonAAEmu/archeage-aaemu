package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum UnitAbilityType {
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

    private final int type;

    public static UnitAbilityType get(byte type) {
        for (UnitAbilityType unitAbilityType : values()) {
            if (unitAbilityType.getType() == type) {
                return unitAbilityType;
            }
        }
        throw new GameServerException("Unknown ability type: " + type);
    }
}
