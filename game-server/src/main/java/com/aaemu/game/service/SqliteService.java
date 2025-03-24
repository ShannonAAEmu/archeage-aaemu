package com.aaemu.game.service;

import com.aaemu.game.repository.dto.character.CharacterEquipPack;
import com.aaemu.game.repository.dto.character.template.Character;
import com.aaemu.game.repository.dto.item.EquipItemAttributeModifier;
import com.aaemu.game.repository.dto.item.GradeDistributionItem;
import com.aaemu.game.repository.dto.item.Holdable;
import com.aaemu.game.repository.dto.item.ItemConfig;
import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.repository.dto.item.SpawnDoodadItem;
import com.aaemu.game.repository.dto.item.Wearable;
import com.aaemu.game.repository.dto.item.WearableKind;
import com.aaemu.game.repository.dto.item.WearableSlot;
import com.aaemu.game.repository.dto.item.template.ItemProcTemplate;
import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.enums.unit.Ability;

import java.util.List;
import java.util.Map;

/**
 * @author Shannon
 */
public interface SqliteService {

    ItemConfig getItemConfig();

    Map<Integer, ItemGrade> getItemGradeMap();

    Map<Integer, Holdable> getHoldableMap();

    Map<Integer, Wearable> getWearableMap();

    Map<Integer, WearableKind> getWearableKindMap();

    Map<Integer, WearableSlot> getWearableSlotMap();

    Map<Integer, EquipItemAttributeModifier> getEquipItemAttributeModifierMap();

    Map<Integer, ItemTemplate> getItemsMap();

    Map<ItemGrade, GradeDistributionItem> getGradeDistributionItemMap();

    Map<Integer, SpawnDoodadItem> getSpawnDoodadItemMap();

    Map<Integer, ItemProcTemplate> getItemProcMap();

    List<Character> getCharacterList();

    Map<Ability, CharacterEquipPack> getCharacterEquipPackList();
}
