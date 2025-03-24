package com.aaemu.game.repository.dto.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeaponItem extends EquipItemTemplate {
    private boolean baseEnchantable;
    private Holdable holdable;
    private boolean baseEquipment;
}
