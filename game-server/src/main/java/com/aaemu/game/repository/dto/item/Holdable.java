package com.aaemu.game.repository.dto.item;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Holdable {
    private int id;
    private int kindId;
    private int speed;
    private int extraDamagePierceFactor;
    private int extraDamageSlashFactor;
    private int extraDamageBluntFactor;
    private int minRange;
    private int maxRange;
    private int angle;
    private int enchantedDps1000;
    private int slotTypeId;
    private int damageScale;
    private String formulaDps;
    private String formulaMdps;
    private String formulaArmor;
    private int sheathePriority;
    private float durabilityRatio;
    private int renewCategory;
    private int itemProcId;
    private int statMultiplier;
}
