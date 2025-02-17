package com.aaemu.game.service.enums.pay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum PayMethod {
    UNKNOWN(0),
    PERSON(1),
    PC_BANG(2),
    TRIAL(3),
    PERSON_TIME(4),
    EVENT(5),
    INVALID(-1);

    private final int code;

    public static PayMethod get(int code) {
        for (PayMethod method : values()) {
            if (method.getCode() == code) {
                return method;
            }
        }
        return INVALID;
    }
}
