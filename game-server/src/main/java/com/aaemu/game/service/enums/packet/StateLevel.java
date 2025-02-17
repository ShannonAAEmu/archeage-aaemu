package com.aaemu.game.service.enums.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum StateLevel {
    _0(0),
    _1(1),
    _2(2);

    private final int state;
}
