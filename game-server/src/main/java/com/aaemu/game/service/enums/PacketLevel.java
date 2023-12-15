package com.aaemu.game.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PacketLevel {
    FIRST(1),
    SECOND(2);

    private final int level;
}
