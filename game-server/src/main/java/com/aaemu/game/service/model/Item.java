package com.aaemu.game.service.model;

import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.service.enums.item.SlotType;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author Shannon
 */
@Data
public class Item {
    private int itemId;
    private int id;
    private ItemGrade itemGrade;
    private ItemFlag itemFlag;
    private int count;  // stackSize
    private SlotType slotType;  // detailType
    private byte[] detail;
    // ImageItemTemplateId (4)
    // Durability (1)
    // ? (2)
    // RuneId (4)
    // gem slot + temper scale A|B (8)
    private OffsetDateTime createTime;
    private int lifespanMins;
    private int madeUnitId; // type
    private int worldId;
}

