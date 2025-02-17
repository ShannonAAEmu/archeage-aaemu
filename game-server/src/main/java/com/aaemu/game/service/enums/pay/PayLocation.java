package com.aaemu.game.service.enums.pay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum PayLocation {
    UNKNOWN(0),
    PERSON(1),
    PC_BANG(2),
    INVALID(-1);

    private final int code;

    public static PayLocation get(int code) {
        for (PayLocation location : values()) {
            if (location.getCode() == code) {
                return location;
            }
        }
        return INVALID;
    }
}
