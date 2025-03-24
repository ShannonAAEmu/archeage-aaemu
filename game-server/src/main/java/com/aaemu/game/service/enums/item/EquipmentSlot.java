package com.aaemu.game.service.enums.item;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum EquipmentSlot {
    HEAD(1),
    NECK(2),
    CHEST(3),
    WAIST(4),
    LEGS(5),
    HANDS(6),
    FEET(7),
    ARMS(8),
    BACK(9),
    EAR_1(10),
    EAR_2(11),
    FINGER_1(12),
    FINGER_2(13),
    UNDERSHIRT(14),
    UNDERPANTS(15),
    MAIN_HAND(16),
    OFF_HAND(17),
    RANGED(18),
    MUSICAL(19),
    FACE(20),
    HAIR(21),
    EYE_LEFT(22),
    EYE_RIGHT(23),
    TAIL(24),
    BODY(25),
    BEARD(26),
    BACKPACK(27),
    COSPLAY(28);

    private final int value;

    public static EquipmentSlot getById(int id) {
        for (EquipmentSlot slot : values()) {
            if (slot.getValue() == id) {
                return slot;
            }
        }
        throw new GameServerException("Unknown equipment item slot: " + id);
    }
}
