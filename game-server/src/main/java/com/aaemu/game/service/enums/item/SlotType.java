package com.aaemu.game.service.enums.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum SlotType {
    NONE((byte) 0),
    EQUIPMENT((byte) 1),
    BAG((byte) 2),
    BANK((byte) 3),
    TRADE((byte) 4),
    MAIL((byte) 5),
    AUCTION((byte) 6),
    EQUIPMENT_MATE((byte) 252),
    SYSTEM((byte) 255);

    private final byte type;
}
