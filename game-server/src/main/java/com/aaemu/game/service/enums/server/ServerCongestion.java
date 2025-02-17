package com.aaemu.game.service.enums.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum ServerCongestion {
    BLUE((byte) 0),
    YELLOW((byte) 1),
    RED((byte) 2);

    private final byte congestion;
}
