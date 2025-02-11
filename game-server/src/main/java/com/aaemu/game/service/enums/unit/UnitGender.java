package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum UnitGender {
    MALE(1),
    FEMALE(2);

    private final int id;

    public static UnitGender get(byte id) {
        for (UnitGender unitGender : values()) {
            if (unitGender.getId() == id) {
                return unitGender;
            }
        }
        throw new GameServerException("Unknown char gender id: " + id);
    }
}
