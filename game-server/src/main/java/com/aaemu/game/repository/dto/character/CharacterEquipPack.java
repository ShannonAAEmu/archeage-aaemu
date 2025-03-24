package com.aaemu.game.repository.dto.character;

import com.aaemu.game.repository.dto.character.template.EquipClothPack;
import com.aaemu.game.repository.dto.character.template.EquipWeaponPack;
import com.aaemu.game.service.enums.unit.Ability;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class CharacterEquipPack {
    private Ability ability;
    private EquipClothPack equipClothPack;
    private EquipWeaponPack equipWeaponPack;
}
