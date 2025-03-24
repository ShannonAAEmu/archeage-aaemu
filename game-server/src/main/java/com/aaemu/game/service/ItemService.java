package com.aaemu.game.service;

import com.aaemu.game.repository.dto.character.template.EquipClothPack;
import com.aaemu.game.repository.dto.character.template.EquipWeaponPack;
import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.enums.item.SlotType;
import com.aaemu.game.service.enums.unit.Ability;
import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;

import java.util.Optional;

/**
 * @author Shannon
 */
public interface ItemService {

    Optional<EquipClothPack> getClothEquipPack(Ability ability);

    Optional<EquipWeaponPack> getWeaponEquipPack(Ability ability);

    Optional<BodyParts> getCharacterBody(Race race, Gender gender);

    ItemTemplate createItem(int itemId, ItemGrade itemGrade, SlotType slotType);

    ItemGrade getItemGrade(int id);
}
