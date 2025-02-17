package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum UnitRace {
    NUIAN((byte) 1),
    FAIRY((byte) 2),
    DWARF((byte) 3),
    ELF((byte) 4),
    HARIHARAN((byte) 5),
    FERRE((byte) 6),
    RETURNED((byte) 7),
    WARBORN((byte) 8);

    private final byte id;

    public static UnitRace get(byte id) {
        for (UnitRace unitRace : values()) {
            if (unitRace.getId() == id) {
                return unitRace;
            }
        }
        throw new GameServerException("Unknown char race id: " + id);
    }
}
