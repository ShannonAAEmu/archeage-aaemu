package com.aaemu.game.service.enums.item;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum EquipmentSlotType {
    HEAD(1),        // wearable slot
    NECK(2),        // wearable slot
    CHEST(3),       // wearable slot
    WAIST(4),       // wearable slot
    LEGS(5),        // wearable slot
    HANDS(6),       // wearable slot
    FEET(7),        // wearable slot
    ARMS(8),        // wearable slot
    BACK(9),        // wearable slot
    EAR(10),        // wearable slot
    FINGER(11),     // wearable slot
    UNDERSHIRT(12), // wearable slot
    UNDERPANTS(13), // wearable slot
    MAIN_HAND(14),
    OFF_HAND(15),
    TWO_HANDED(16),
    ONE_HANDED(17),
    RANGED(18),
    AMMUNITION(19),
    SHIELD(20),
    INSTRUMENT(21),
    BAG(22),
    FACE(23),       // body_part
    HAIR(24),       // body_part
    EYE_LEFT(25),   // body_part
    EYE_RIGHT(26),  // body_part
    TAIL(27),       // body_part
    BODY(28),       // body_part
    BEARD(29),      // body_part
    BACKPACK(30),
    COSPLAY(31);    // wearable slot

    private final int value;

    public static EquipmentSlotType getById(int id) {
        for (EquipmentSlotType type : values()) {
            if (type.getValue() == id) {
                return type;
            }
        }
        throw new GameServerException("Unknown equipment item slot type: " + id);
    }
}
