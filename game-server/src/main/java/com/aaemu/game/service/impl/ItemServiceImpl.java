package com.aaemu.game.service.impl;

import com.aaemu.game.repository.dto.character.CharacterEquipPack;
import com.aaemu.game.repository.dto.character.template.Character;
import com.aaemu.game.repository.dto.character.template.EquipClothPack;
import com.aaemu.game.repository.dto.character.template.EquipWeaponPack;
import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.ItemService;
import com.aaemu.game.service.SqliteService;
import com.aaemu.game.service.enums.item.SlotType;
import com.aaemu.game.service.enums.unit.Ability;
import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final SqliteService sqliteService;

    @Override
    public Optional<EquipClothPack> getClothEquipPack(Ability ability) {
        CharacterEquipPack characterEquipPack = sqliteService.getCharacterEquipPackList().get(ability);
        if (Objects.isNull(characterEquipPack)) {
            return Optional.empty();
        }
        return Optional.of(characterEquipPack.getEquipClothPack());
    }

    @Override
    public Optional<EquipWeaponPack> getWeaponEquipPack(Ability ability) {
        CharacterEquipPack characterEquipPack = sqliteService.getCharacterEquipPackList().get(ability);
        if (Objects.isNull(characterEquipPack)) {
            return Optional.empty();
        }
        return Optional.of(characterEquipPack.getEquipWeaponPack());
    }

    @Override
    public Optional<BodyParts> getCharacterBody(Race race, Gender gender) {
        List<Character> characterList = sqliteService.getCharacterList();
        for (Character character : characterList) {
            if (character.getRace().equals(race) && character.getGender().equals(gender)) {
                return Optional.of(character.getBodyParts());
            }
        }
        return Optional.empty();
    }

    @Override
    public ItemTemplate createItem(int itemId, ItemGrade itemGrade, SlotType slotType) {
        ItemTemplate itemTemplate = sqliteService.getItemsMap().get(itemId);
        if (Objects.isNull(itemTemplate)) {
            return new ItemTemplate();
        }
        return new ItemTemplate();
    }

    @Override
    public ItemGrade getItemGrade(int id) {
        return sqliteService.getItemGradeMap().get(id);
    }
}
