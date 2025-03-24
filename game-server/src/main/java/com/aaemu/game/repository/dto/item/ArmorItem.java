package com.aaemu.game.repository.dto.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArmorItem extends EquipItemTemplate {
    private Wearable wearable;
    private WearableKind wearableKind;
    private WearableSlot wearableSlot;
    private boolean baseEnchantable;
    private boolean baseEquipment;

}
