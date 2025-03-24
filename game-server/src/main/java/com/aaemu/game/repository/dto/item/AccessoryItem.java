package com.aaemu.game.repository.dto.item;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AccessoryItem extends EquipItemTemplate {
    private Wearable wearableTemplate;
    private WearableKind kindTemplate;
    private WearableSlot slotTemplate;
}
