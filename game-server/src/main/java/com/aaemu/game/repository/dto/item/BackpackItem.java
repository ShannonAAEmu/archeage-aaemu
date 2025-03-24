package com.aaemu.game.repository.dto.item;

import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.enums.item.BackpackType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BackpackItem extends ItemTemplate {
    private int assetId;
    private int asset2Id;
    private BackpackType backpackType;
    private int declareSiegeZoneGroupId;
    private boolean isHeavy;
}

