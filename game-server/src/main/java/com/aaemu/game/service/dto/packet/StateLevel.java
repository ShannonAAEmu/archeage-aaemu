package com.aaemu.game.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StateLevel {
    ZERO(0),
    FIRST(1),
    SECOND(1);

    private final int state;
}
