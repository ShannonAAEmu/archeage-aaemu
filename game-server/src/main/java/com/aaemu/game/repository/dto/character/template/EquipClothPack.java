package com.aaemu.game.repository.dto.character.template;

import com.aaemu.game.repository.dto.item.ItemGrade;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class EquipClothPack {
    private int headId;
    private ItemGrade headGrade;
    private int neckId;
    private ItemGrade neckGrade;
    private int chestId;
    private ItemGrade chestGrade;
    private int waistId;
    private ItemGrade waistGrade;
    private int legsId;
    private ItemGrade legsGrade;
    private int handsId;
    private ItemGrade handsGrade;
    private int feetId;
    private ItemGrade feetGrade;
    private int armsId;
    private ItemGrade armsGrade;
    private int backId;
    private ItemGrade backGrade;
    private int cosplayId;
    private ItemGrade cosplayGrade;
    private int undershirtId;
    private ItemGrade undershirtGrade;
    private int underpantsId;
    private ItemGrade underpantsGrade;
}
