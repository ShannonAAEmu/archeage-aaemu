package com.aaemu.game.service.enums.item;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BackpackType {
    CASTLE_CLAIM(1),
    GLIDER(2),
    TRADE_PACK(3),
    SIEGE_DECLARE(4),
    NATION_FLAG(5),
    FISH(6),
    UNKNOWN(7);

    private final int value;

    public static BackpackType getBackpackType(int type) {
        for (BackpackType t : values()) {
            if (t.value == type) {
                return t;
            }
        }
        throw new GameServerException("Invalid Backpack Type: " + type);
    }
}
