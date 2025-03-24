package com.aaemu.game.repository.dto.item.template;

import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.service.enums.item.BindType;
import com.aaemu.game.service.model.Item;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Shannon
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ItemTemplate extends Item {
    private int itemId;
    private String name;
    private int categoryId;
    private int level;
    private int levelLimit;
    private int price;
    private int refund;
    private BindType bindType;
    private int pickupLimit;
    private int maxCount;
    private boolean sellable;
    private int useSkillId;
    private boolean useSkillAsReagent;
    private int buffId;
    private boolean gradable;
    private boolean lootMulti;
    private int lootQuestId;
    private int honorPrice;
    private int expAbsLifetime;
    private int expOnlineLifetime;
    private int expDate;
    private int levelRequirement;
    private int auctionCategoryA;
    private int auctionCategoryB;
    private int auctionCategoryC;
    private ItemGrade itemGrade;
    private int LivingPointPrice;
    private String searchString;
}

