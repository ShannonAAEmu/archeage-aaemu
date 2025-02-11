package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum UnitCustomModelType {
    NONE((byte) 0),
    HAIR((byte) 1),
    SKIN((byte) 2),
    HEAD((byte) 3);

    private final byte type;

    public static UnitCustomModelType get(byte type) {
        for (UnitCustomModelType modelType : values()) {
            if (modelType.getType() == type) {
                return modelType;
            }
        }
        throw new GameServerException("Unknown unit custom model type: " + type);
    }
}
