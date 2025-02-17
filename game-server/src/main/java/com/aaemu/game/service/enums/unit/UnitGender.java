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
    MALE((byte) 1),
    FEMALE((byte) 2);

    private final byte id;

    public static UnitGender get(byte id) {
        for (UnitGender unitGender : values()) {
            if (unitGender.getId() == id) {
                return unitGender;
            }
        }
        throw new GameServerException("Unknown char gender id: " + id);
    }
}
