package com.aaemu.game.repository.dto.item;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class WearableKind {
    private int armorTypeId;
    private int armorRatio;
    private int magicResistanceRatio;
    private int fullBuffId;
    private int halfBuffId;
    private int extraDamagePierce;
    private int extraDamageSlash;
    private int extraDamageBlunt;
    private float durabilityRatio;
}
