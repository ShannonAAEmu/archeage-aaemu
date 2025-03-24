package com.aaemu.game.repository.dto.item;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ItemConfig {
    private int id;
    private float durabilityDecrementChance;
    private float durabilityRepairCostFactor;
    private float durabilityConst;
    private float holdableDurabilityConst;
    private float wearableDurabilityConst;
    private int deathDurabilityLossRatio;
    private int itemStatConst;
    private int holdableStatConst;
    private int wearableStatConst;
    private int statValueConst;
}
