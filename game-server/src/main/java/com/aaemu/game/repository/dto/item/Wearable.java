package com.aaemu.game.repository.dto.item;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Wearable {
    private int id;
    private int armorTypeId;
    private int slotTypeId;
    private int armorBp;
    private int magicResistanceBp;
}
