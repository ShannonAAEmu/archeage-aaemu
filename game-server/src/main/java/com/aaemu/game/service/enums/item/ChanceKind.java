package com.aaemu.game.service.enums.item;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum ChanceKind {
    HIT_ANY(1),
    HIT_MELEE(2),
    HIT_MELEE_CRIT(3),
    HIT_SPELL(4),
    HIT_SPELL_CRIT(5),
    HIT_RANGED(6),
    HIT_RANGED_CRIT(7),
    HIT_SIEGE(8),
    TAKE_DAMAGE_ANY(9),
    TAKE_DAMAGE_MELEE(10),
    TAKE_DAMAGE_MELEE_CRIT(11),
    TAKE_DAMAGE_SPELL(12),
    TAKE_DAMAGE_SPELL_CRIT(13),
    TAKE_DAMAGE_RANGED(14),
    TAKE_DAMAGE_RANGED_CRIT(15),
    TAKE_DAMAGE_SIEGE(16),
    HIT_HEAL(17),
    HIT_HEAL_CRIT(18),
    FIRE_SKILL(19),
    HIT_SKILL(20);

    private final int value;

    public static ChanceKind getByKind(int kind) {
        for (ChanceKind k : values()) {
            if (k.getValue() == kind) {
                return k;
            }
        }
        throw new GameServerException("Unknown proc chance kind: " + kind);
    }
}
