package com.aaemu.game.repository.dto.item;

import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BodyPartItem extends ItemTemplate {
    private int modelId;
    private boolean npcOnly;
}
