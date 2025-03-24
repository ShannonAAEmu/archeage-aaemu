package com.aaemu.game.repository.dto.item;

import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EquipItemTemplate extends ItemTemplate {
    private int modSetId;
    private boolean repairable;
    private int durabilityMultiplier;
    private int rechargeBuffId;
    private int chargeLifetime;
    private int chargeCount;
}
