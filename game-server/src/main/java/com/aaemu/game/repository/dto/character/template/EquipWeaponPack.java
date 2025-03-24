package com.aaemu.game.repository.dto.character.template;

import com.aaemu.game.repository.dto.item.ItemGrade;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class EquipWeaponPack {
    private int mainHandId;
    private ItemGrade mainHandGrade;
    private int offHandId;
    private ItemGrade offHandGrade;
    private int rangedId;
    private ItemGrade rangedGrade;
    private int musicalId;
    private ItemGrade musicalGrade;
}
