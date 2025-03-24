package com.aaemu.game.repository.dto.item.template;

import com.aaemu.game.service.enums.item.ChanceKind;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class ItemProcTemplate {
    private int id;
    private int skillId;
    private ChanceKind chanceKind;
    private int chanceRate;
    private int chanceParam;
    private int cooldownSec;
    private boolean finisher;
    private int itemLevelBasedChanceBonus;
}
