package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@Getter
@RequiredArgsConstructor
public enum FractionType {
    INVALID(0),
    ALWAYS_FRIENDLY(1),
    ALWAYS_NEUTRAL(2),
    ALWAYS_HOSTILE(3),
    NUIAN(101),
    ELF(103),
    HARIHARAN(109),
    FERRE(113);

    private final int type;

    public static FractionType getByRace(Race race) {
        return switch (race) {
            case NUIAN -> NUIAN;
            case ELF -> ELF;
            case HARIHARAN -> HARIHARAN;
            case FERRE -> FERRE;
            default -> throw new GameServerException("Unknown fraction type for: " + race);
        };
    }
}
