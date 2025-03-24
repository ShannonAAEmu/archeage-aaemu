package com.aaemu.game.repository.dto.item;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ItemGrade {
    private int id;
    private String name;
    private int gradeOrder;
    private float holdableDps;
    private float holdableArmor;
    private float holdableMagicDps;
    private float wearableArmor;
    private float wearableMagicResistance;
    private float durability;
    private int upgradeRatio;
    private int statMultiplier;
    private int refundMultiplier;
}
