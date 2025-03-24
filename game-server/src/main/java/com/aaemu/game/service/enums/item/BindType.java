package com.aaemu.game.service.enums.item;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BindType {
    NORMAL(1),
    BIND_ON_PICKUP(2),
    BIND_ON_EQUIP(3),
    BIND_ON_UNPACK(4),
    BIND_ON_PICKUP_PACK(5),
    BIND_ON_AUCTION_WIN(6);

    private final int value;

    public static BindType getByType(int type) {
        for (BindType bindType : values()) {
            if (bindType.getValue() == type) {
                return bindType;
            }
        }
        throw new GameServerException("Invalid item bind type: " + type);
    }
}
