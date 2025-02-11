package com.aaemu.game.service.enums;

import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
public enum ServerAvailability {
    AVAILABLE(true),
    NOT_AVAILABLE(false);

    private final boolean status;

    public boolean getStatus() {
        return status;
    }
}
