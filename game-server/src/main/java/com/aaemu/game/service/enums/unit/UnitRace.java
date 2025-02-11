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
    NUIAN(1),
    FAIRY(2),
    DWARF(3),
    ELF(4),
    HARIHARAN(5),
    FERRE(6),
    RETURNED(7),
    WARBORN(8);

    private final int id;

    public static UnitRace get(byte id) {
        for (UnitRace unitRace : values()) {
            if (unitRace.getId() == id) {
                return unitRace;
            }
        }
        throw new GameServerException("Unknown char race id: " + id);
    }
}
