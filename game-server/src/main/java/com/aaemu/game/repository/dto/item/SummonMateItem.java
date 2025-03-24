package com.aaemu.game.repository.dto.item;

import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SummonMateItem extends ItemTemplate {
    private long npcId;
}
