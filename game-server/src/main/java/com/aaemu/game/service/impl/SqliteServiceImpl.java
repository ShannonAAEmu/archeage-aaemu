package com.aaemu.game.service.impl;

import com.aaemu.game.repository.dto.character.CharacterEquipPack;
import com.aaemu.game.repository.dto.character.template.Character;
import com.aaemu.game.repository.dto.character.template.EquipClothPack;
import com.aaemu.game.repository.dto.character.template.EquipWeaponPack;
import com.aaemu.game.repository.dto.item.AccessoryItem;
import com.aaemu.game.repository.dto.item.ArmorItem;
import com.aaemu.game.repository.dto.item.BackpackItem;
import com.aaemu.game.repository.dto.item.BodyPartItem;
import com.aaemu.game.repository.dto.item.EquipItemAttributeModifier;
import com.aaemu.game.repository.dto.item.GradeDistributionItem;
import com.aaemu.game.repository.dto.item.Holdable;
import com.aaemu.game.repository.dto.item.ItemConfig;
import com.aaemu.game.repository.dto.item.ItemGrade;
import com.aaemu.game.repository.dto.item.RuneItem;
import com.aaemu.game.repository.dto.item.SpawnDoodadItem;
import com.aaemu.game.repository.dto.item.SummonMateItem;
import com.aaemu.game.repository.dto.item.WeaponItem;
import com.aaemu.game.repository.dto.item.Wearable;
import com.aaemu.game.repository.dto.item.WearableKind;
import com.aaemu.game.repository.dto.item.WearableSlot;
import com.aaemu.game.repository.dto.item.template.ItemProcTemplate;
import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.SqliteService;
import com.aaemu.game.service.enums.item.BackpackType;
import com.aaemu.game.service.enums.item.BindType;
import com.aaemu.game.service.enums.item.ChanceKind;
import com.aaemu.game.service.enums.unit.Ability;
import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import com.aaemu.game.service.exception.GameServerException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.sqlite.SQLiteConfig;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author Shannon
 */
@Service
@Log4j2
public class SqliteServiceImpl implements SqliteService {
    private final Connection connection;
    private ItemConfig itemConfig;
    private Map<Integer, ItemGrade> itemGradeMap;
    private Map<Integer, Holdable> holdableMap;
    private Map<Integer, Wearable> wearableMap;
    private Map<Integer, WearableKind> wearableKindMap;
    private Map<Integer, WearableSlot> wearableSlotMap;
    private Map<Integer, EquipItemAttributeModifier> equipItemAttributeModifierMap;
    private Map<Integer, ItemTemplate> itemsMap;
    private Map<ItemGrade, GradeDistributionItem> gradeDistributionItemMap;
    private Map<Integer, SpawnDoodadItem> spawnDoodadItemMap;
    private Map<Integer, ItemProcTemplate> itemProcMap;
    private List<Character> characterList;
    private Map<Ability, CharacterEquipPack> characterEquipPackList;

    public SqliteServiceImpl() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSynchronous(SQLiteConfig.SynchronousMode.OFF);
        config.setPragma(SQLiteConfig.Pragma.MMAP_SIZE, "468435456");
        config.setLockingMode(SQLiteConfig.LockingMode.EXCLUSIVE);
        config.setJournalMode(SQLiteConfig.JournalMode.OFF);
        config.setTempStore(SQLiteConfig.TempStore.MEMORY);
        this.connection = config.createConnection("jdbc:sqlite:" + Path.of(System.getProperty("user.dir")).resolve("db").resolve("compact.sqlite3"));
        this.connection.setAutoCommit(false);
        loadItemsInfo();
        loadCharactersInfo();
    }

    public void loadItemsInfo() {
//        loadItemConfig();
        loadItemGrades();
//        loadHoldables();
//        loadWearables();
//        loadWearableKinds();
//        loadWearableSlots();
//        loadEquipItemAttributeModifiers();
//        loadArmorItems();
//        loadWeaponItems();
//        loadAccessoryItems();
//        loadSummonMateItems();
//        loadBodyPartItems();
//        loadEnchantingGemItems();
//        loadBackpacksItems();
//        loadGradeDistributionsItems();
//        loadSpawnDoodadItems();
//        loadItemProcs();
        loadItems();
    }

    private void loadCharactersInfo() {
        loadCharacters();
        loadItemBodyParts();
        loadCharacterEquipPacks();
    }

    private void loadItemConfig() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_configs");
            connection.commit();
            itemConfig = new ItemConfig();
            int columnIndex = 1;
            if (resultSet.next()) {
                itemConfig.setId(resultSet.getInt(columnIndex++));
                itemConfig.setDurabilityDecrementChance(resultSet.getFloat(columnIndex++));
                itemConfig.setDurabilityRepairCostFactor(resultSet.getFloat(columnIndex++));
                itemConfig.setDurabilityConst(resultSet.getFloat(columnIndex++));
                itemConfig.setHoldableDurabilityConst(resultSet.getFloat(columnIndex++));
                itemConfig.setWearableDurabilityConst(resultSet.getFloat(columnIndex++));
                itemConfig.setDeathDurabilityLossRatio(resultSet.getInt(columnIndex++));
                itemConfig.setItemStatConst(resultSet.getInt(columnIndex++));
                itemConfig.setHoldableStatConst(resultSet.getInt(columnIndex++));
                itemConfig.setWearableStatConst(resultSet.getInt(columnIndex++));
                itemConfig.setStatValueConst(resultSet.getInt(columnIndex));
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadItemGrades() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_grades");
            connection.commit();
            itemGradeMap = new TreeMap<>();
            while (resultSet.next()) {
                ItemGrade itemGrade = new ItemGrade();
                itemGrade.setId(resultSet.getInt("id"));
                itemGrade.setName(resultSet.getString("name"));
                itemGrade.setGradeOrder(resultSet.getInt("grade_order"));
                itemGrade.setHoldableDps(resultSet.getFloat("var_holdable_dps"));
                itemGrade.setHoldableArmor(resultSet.getFloat("var_holdable_armor"));
                itemGrade.setHoldableMagicDps(resultSet.getFloat("var_holdable_magic_dps"));
                itemGrade.setWearableArmor(resultSet.getFloat("var_wearable_armor"));
                itemGrade.setWearableMagicResistance(resultSet.getFloat("var_wearable_magic_resistance"));
                itemGrade.setDurability(resultSet.getFloat("durability_value"));
                itemGrade.setUpgradeRatio(resultSet.getInt("upgrade_ratio"));
                itemGrade.setStatMultiplier(resultSet.getInt("stat_multiplier"));
                itemGrade.setRefundMultiplier(resultSet.getInt("refund_multiplier"));
                this.itemGradeMap.put(itemGrade.getId(), itemGrade);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadHoldables() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM holdables");
            connection.commit();
            holdableMap = new TreeMap<>();
            while (resultSet.next()) {
                Holdable holdable = new Holdable();
                holdable.setId(resultSet.getInt("id"));
                holdable.setKindId(resultSet.getInt("kind_id"));
                holdable.setSpeed(resultSet.getInt("speed"));
                holdable.setExtraDamagePierceFactor(resultSet.getInt("extra_damage_pierce_factor"));
                holdable.setExtraDamageSlashFactor(resultSet.getInt("extra_damage_slash_factor"));
                holdable.setExtraDamageBluntFactor(resultSet.getInt("extra_damage_blunt_factor"));
                holdable.setMinRange(resultSet.getInt("min_range"));
                holdable.setMaxRange(resultSet.getInt("max_range"));
                holdable.setAngle(resultSet.getInt("angle"));
                holdable.setEnchantedDps1000(resultSet.getInt("enchanted_dps1000"));
                holdable.setSlotTypeId(resultSet.getInt("slot_type_id"));
                holdable.setDamageScale(resultSet.getInt("damage_scale"));
                holdable.setFormulaDps(resultSet.getString("formula_dps"));
                holdable.setFormulaArmor(resultSet.getString("formula_mdps"));
                holdable.setFormulaArmor(resultSet.getString("formula_armor"));
                holdable.setSheathePriority(resultSet.getInt("sheathe_priority"));
                holdable.setDurabilityRatio(resultSet.getInt("durability_ratio"));
                holdable.setRenewCategory(resultSet.getInt("renew_category"));
                holdable.setItemProcId(resultSet.getInt("item_proc_id"));
                holdable.setStatMultiplier(resultSet.getInt("stat_multiplier"));
                holdableMap.put(holdable.getId(), holdable);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadWearables() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM wearables");
            connection.commit();
            wearableMap = new TreeMap<>();
            while (resultSet.next()) {
                Wearable wearable = new Wearable();
                wearable.setId(resultSet.getInt("id"));
                wearable.setArmorTypeId(resultSet.getInt("armor_type_id"));
                wearable.setSlotTypeId(resultSet.getInt("slot_type_id"));
                wearable.setArmorBp(resultSet.getInt("armor_bp"));
                wearable.setMagicResistanceBp(resultSet.getInt("magic_resistance_bp"));
                wearableMap.put(wearable.getId(), wearable);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadWearableKinds() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM wearable_kinds");
            connection.commit();
            wearableKindMap = new TreeMap<>();
            while (resultSet.next()) {
                WearableKind wearableKind = new WearableKind();
                wearableKind.setArmorTypeId(resultSet.getInt("armor_type_id"));
                wearableKind.setArmorRatio(resultSet.getInt("armor_ratio"));
                wearableKind.setMagicResistanceRatio(resultSet.getInt("magic_resistance_ratio"));
                wearableKind.setFullBuffId(resultSet.getInt("full_buff_id"));
                wearableKind.setHalfBuffId(resultSet.getInt("half_buff_id"));
                wearableKind.setExtraDamagePierce(resultSet.getInt("extra_damage_pierce"));
                wearableKind.setExtraDamageSlash(resultSet.getInt("extra_damage_slash"));
                wearableKind.setExtraDamageBlunt(resultSet.getInt("extra_damage_blunt"));
                wearableKind.setDurabilityRatio(resultSet.getInt("durability_ratio"));
                wearableKindMap.put(wearableKind.getArmorTypeId(), wearableKind);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadWearableSlots() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM wearable_slots");
            connection.commit();
            wearableSlotMap = new TreeMap<>();
            while (resultSet.next()) {
                WearableSlot wearableSlot = new WearableSlot();
                wearableSlot.setSlotTypeId(resultSet.getInt("slot_type_id"));
                wearableSlot.setCoverage(resultSet.getInt("coverage"));
                wearableSlotMap.put(wearableSlot.getSlotTypeId(), wearableSlot);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadEquipItemAttributeModifiers() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM equip_item_attr_modifiers");
            connection.commit();
            equipItemAttributeModifierMap = new TreeMap<>();
            while (resultSet.next()) {
                EquipItemAttributeModifier equipItemAttributeModifier = new EquipItemAttributeModifier();
                equipItemAttributeModifier.setId(resultSet.getInt("id"));
                equipItemAttributeModifier.setStrWeight(resultSet.getInt("str_weight"));
                equipItemAttributeModifier.setDexWeight(resultSet.getInt("dex_weight"));
                equipItemAttributeModifier.setStaWeight(resultSet.getInt("sta_weight"));
                equipItemAttributeModifier.setIntWeight(resultSet.getInt("int_weight"));
                equipItemAttributeModifier.setSpiWeight(resultSet.getInt("spi_weight"));
                equipItemAttributeModifierMap.put(equipItemAttributeModifier.getId(), equipItemAttributeModifier);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadArmorItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_armors");
            connection.commit();
            itemsMap = new TreeMap<>();
            while (resultSet.next()) {
                int slotTypeId = resultSet.getInt("slot_type_id");
                int armorTypeId = resultSet.getInt("type_id");
                ArmorItem armorItem = new ArmorItem();
                armorItem.setItemId(resultSet.getInt("item_id"));
                armorItem.setWearable(getWearableEntity(armorTypeId, slotTypeId).orElse(null));
                armorItem.setWearableKind(wearableKindMap.get(armorTypeId));
                armorItem.setWearableSlot(wearableSlotMap.get(slotTypeId));
                armorItem.setBaseEnchantable(resultSet.getBoolean("base_enchantable"));
                armorItem.setModSetId(resultSet.getInt("mod_set_id"));
                armorItem.setRepairable(resultSet.getBoolean("repairable"));
                armorItem.setDurabilityMultiplier(resultSet.getInt("durability_multiplier"));
                armorItem.setBaseEquipment(resultSet.getBoolean("base_equipment"));
                armorItem.setRechargeBuffId(resultSet.getInt("recharge_buff_id"));
                armorItem.setChargeLifetime(resultSet.getInt("charge_lifetime"));
                armorItem.setChargeCount(resultSet.getInt("charge_count"));
                if (itemsMap.containsKey(armorItem.getItemId())) {  // TODO check exists
                    log.warn("Armor item {} {}", armorItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(armorItem.getItemId(), armorItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadWeaponItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_weapons");
            connection.commit();
            while (resultSet.next()) {
                int holdableId = resultSet.getInt("holdable_id");
                WeaponItem weaponItem = new WeaponItem();
                weaponItem.setItemId(resultSet.getInt("item_id"));
                weaponItem.setBaseEnchantable(resultSet.getBoolean("base_enchantable"));
                weaponItem.setHoldable(holdableMap.get(holdableId));
                weaponItem.setModSetId(resultSet.getInt("mod_set_id"));
                weaponItem.setRepairable(resultSet.getBoolean("repairable"));
                weaponItem.setDurabilityMultiplier(resultSet.getInt("durability_multiplier"));
                weaponItem.setBaseEquipment(resultSet.getBoolean("base_equipment"));
                weaponItem.setRechargeBuffId(resultSet.getInt("recharge_buff_id"));
                weaponItem.setChargeLifetime(resultSet.getInt("charge_lifetime"));
                weaponItem.setChargeCount(resultSet.getInt("charge_count"));
                if (itemsMap.containsKey(weaponItem.getItemId())) {  // TODO check exists
                    log.warn("Weapon item {} {}", weaponItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(weaponItem.getItemId(), weaponItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadAccessoryItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_accessories");
            connection.commit();
            while (resultSet.next()) {
                int slotTypeId = resultSet.getInt("slot_type_id");
                int armorTypeId = resultSet.getInt("type_id");
                AccessoryItem accessoryItem = new AccessoryItem();
                accessoryItem.setItemId(resultSet.getInt("item_id"));
                accessoryItem.setWearableTemplate(getWearableEntity(armorTypeId, slotTypeId).orElse(null));
                accessoryItem.setKindTemplate(wearableKindMap.get(armorTypeId));
                accessoryItem.setSlotTemplate(wearableSlotMap.get(slotTypeId));
                accessoryItem.setModSetId(resultSet.getInt("mod_set_id"));
                accessoryItem.setRepairable(resultSet.getBoolean("repairable"));
                accessoryItem.setDurabilityMultiplier(resultSet.getInt("durability_multiplier"));
                accessoryItem.setRechargeBuffId(resultSet.getInt("recharge_buff_id"));
                accessoryItem.setChargeLifetime(resultSet.getInt("charge_lifetime"));
                accessoryItem.setChargeCount(resultSet.getInt("charge_count"));
                if (itemsMap.containsKey(accessoryItem.getItemId())) {  // TODO check exists
                    log.warn("Weapon item {} {}", accessoryItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(accessoryItem.getItemId(), accessoryItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadSummonMateItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_summon_mates");
            connection.commit();
            while (resultSet.next()) {
                SummonMateItem summonMateItem = new SummonMateItem();
                summonMateItem.setItemId(resultSet.getInt("item_id"));
                summonMateItem.setNpcId(resultSet.getInt("npc_id"));
                if (itemsMap.containsKey(summonMateItem.getItemId())) {  // TODO check exists
                    log.warn("Summon mate item {} {}", summonMateItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(summonMateItem.getItemId(), summonMateItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadBodyPartItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_body_parts");
            connection.commit();
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                if (itemId == 0) {
                    continue;
                }
                BodyPartItem bodyPartItem = new BodyPartItem();
                bodyPartItem.setItemId(itemId);
                bodyPartItem.setModelId(resultSet.getInt("model_id"));
                bodyPartItem.setNpcOnly(resultSet.getBoolean("npc_only"));
                if (itemsMap.containsKey(bodyPartItem.getItemId())) {  // TODO check exists
                    log.warn("Body parts item {} {}", bodyPartItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(bodyPartItem.getItemId(), bodyPartItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadEnchantingGemItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_enchanting_gems");
            connection.commit();
            while (resultSet.next()) {
                RuneItem runeItem = new RuneItem();
                runeItem.setItemId(resultSet.getInt("item_id"));
                runeItem.setEquipSlotGroupId(resultSet.getInt("equip_slot_group_id"));
                runeItem.setEquipLevel(resultSet.getByte("equip_level"));
                runeItem.setItemGradeId(resultSet.getByte("item_grade_id"));
                if (itemsMap.containsKey(runeItem.getItemId())) {  // TODO check exists
                    log.warn("Body parts item {} {}", runeItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(runeItem.getItemId(), runeItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadBackpacksItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_backpacks");
            connection.commit();
            while (resultSet.next()) {
                BackpackItem backpackItem = new BackpackItem();
                backpackItem.setItemId(resultSet.getInt("item_id"));
                backpackItem.setAssetId(resultSet.getInt("asset_id"));
                backpackItem.setBackpackType(BackpackType.getBackpackType(resultSet.getInt("backpack_type_id")));
                backpackItem.setDeclareSiegeZoneGroupId(resultSet.getInt("declare_siege_zone_group_id"));
                backpackItem.setHeavy(resultSet.getBoolean("heavy"));
                backpackItem.setAsset2Id(resultSet.getInt("asset2_id"));
                if (itemsMap.containsKey(backpackItem.getItemId())) {  // TODO check exists
                    log.warn("Body parts item {} {}", backpackItem.getItemId(), "already exists");
                    System.exit(1);
                }
                itemsMap.put(backpackItem.getItemId(), backpackItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadGradeDistributionsItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_grade_distributions");
            connection.commit();
            gradeDistributionItemMap = new HashMap<>();
            while (resultSet.next()) {
                GradeDistributionItem gradeDistributionItem = new GradeDistributionItem();
                gradeDistributionItem.setItemGrade(itemGradeMap.get(resultSet.getInt("id")));
                gradeDistributionItem.setName(resultSet.getString("name"));
                gradeDistributionItem.setWeight0(resultSet.getInt("weight_0"));
                gradeDistributionItem.setWeight1(resultSet.getInt("weight_1"));
                gradeDistributionItem.setWeight2(resultSet.getInt("weight_2"));
                gradeDistributionItem.setWeight3(resultSet.getInt("weight_3"));
                gradeDistributionItem.setWeight4(resultSet.getInt("weight_4"));
                gradeDistributionItem.setWeight5(resultSet.getInt("weight_5"));
                gradeDistributionItem.setWeight6(resultSet.getInt("weight_6"));
                gradeDistributionItem.setWeight7(resultSet.getInt("weight_7"));
                gradeDistributionItem.setWeight8(resultSet.getInt("weight_8"));
                gradeDistributionItem.setWeight9(resultSet.getInt("weight_9"));
                gradeDistributionItem.setWeight10(resultSet.getInt("weight_10"));
                gradeDistributionItem.setWeight11(resultSet.getInt("weight_11"));
                gradeDistributionItemMap.put(gradeDistributionItem.getItemGrade(), gradeDistributionItem);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadSpawnDoodadItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_spawn_doodads");
            connection.commit();
            spawnDoodadItemMap = new TreeMap<>();
            while (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                int doodadId = resultSet.getInt("doodad_id");
                if (spawnDoodadItemMap.containsKey(doodadId)) {
                    spawnDoodadItemMap.get(doodadId).getItemIds().add(itemId);
                } else {
                    SpawnDoodadItem spawnDoodadItem = new SpawnDoodadItem();
                    spawnDoodadItem.setDoodadId(doodadId);
                    spawnDoodadItem.getItemIds().add(itemId);
                    spawnDoodadItemMap.put(spawnDoodadItem.getDoodadId(), spawnDoodadItem);
                }
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadItemProcs() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM item_procs");
            connection.commit();
            itemProcMap = new TreeMap<>();
            while (resultSet.next()) {
                ItemProcTemplate itemProcTemplate = new ItemProcTemplate();
                itemProcTemplate.setId(resultSet.getInt("id"));
                itemProcTemplate.setSkillId(resultSet.getInt("skill_id"));
                itemProcTemplate.setChanceKind(ChanceKind.getByKind(resultSet.getInt("chance_kind_id")));
                itemProcTemplate.setChanceRate(resultSet.getInt("chance_rate"));
                itemProcTemplate.setChanceParam(resultSet.getInt("chance_param"));
                itemProcTemplate.setCooldownSec(resultSet.getInt("cooldown_sec"));
                itemProcTemplate.setFinisher(resultSet.getBoolean("finisher"));
                itemProcTemplate.setItemLevelBasedChanceBonus(resultSet.getInt("item_level_based_chance_bonus"));
                itemProcMap.put(itemProcTemplate.getId(), itemProcTemplate);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadItems() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM items");
            connection.commit();
            itemsMap = new TreeMap<>(); // TODO change init
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
//                if (itemsMap.containsKey(id)) {
//                    log.warn("Item {} {}", id, "already exists");
//                    System.exit(1);
//                }
                ItemTemplate itemTemplate = new ItemTemplate();
                itemTemplate.setId(id);
                itemTemplate.setName(resultSet.getString("name"));
                itemTemplate.setCategoryId(resultSet.getInt("category_id"));
                itemTemplate.setLevel(resultSet.getInt("level"));
                itemTemplate.setPrice(resultSet.getInt("price"));
                itemTemplate.setRefund(resultSet.getInt("refund"));
                itemTemplate.setBindType(BindType.getByType(resultSet.getByte("bind_id")));
                itemTemplate.setPickupLimit(resultSet.getInt("pickup_limit"));
                itemTemplate.setMaxCount(resultSet.getInt("max_stack_size"));
                itemTemplate.setSellable(resultSet.getBoolean("sellable"));
                itemTemplate.setUseSkillId(resultSet.getInt("use_skill_id"));
                itemTemplate.setUseSkillAsReagent(resultSet.getBoolean("use_skill_as_reagent"));
                itemTemplate.setBuffId(resultSet.getInt("buff_id"));
                itemTemplate.setGradable(resultSet.getBoolean("gradable"));
                itemTemplate.setLootMulti(resultSet.getBoolean("loot_multi"));
                itemTemplate.setLootQuestId(resultSet.getInt("loot_quest_id"));
                itemTemplate.setHonorPrice(resultSet.getInt("honor_price"));
                itemTemplate.setExpAbsLifetime(resultSet.getInt("exp_abs_lifetime"));
                itemTemplate.setExpOnlineLifetime(resultSet.getInt("exp_online_lifetime"));
                itemTemplate.setExpDate(resultSet.getInt("exp_date"));
                itemTemplate.setLevelRequirement(resultSet.getInt("level_requirement"));
                itemTemplate.setAuctionCategoryA(resultSet.getInt("auction_a_category_id"));
                itemTemplate.setAuctionCategoryB(resultSet.getInt("auction_b_category_id"));
                itemTemplate.setAuctionCategoryC(resultSet.getInt("auction_c_category_id"));
                itemTemplate.setLevelLimit(resultSet.getInt("level_limit"));
                itemTemplate.setItemGrade(itemGradeMap.get(resultSet.getInt("fixed_grade")));
                itemTemplate.setLivingPointPrice(resultSet.getInt("living_point_price"));
//                if (itemsMap.containsKey(itemTemplate.getId())) {
//                    log.warn("Item: {} {}", itemTemplate.getId(), "already exists");
//                    System.exit(1);
//                }
                itemsMap.put(id, itemTemplate);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private Optional<Wearable> getWearableEntity(int armorTypeId, int slotTypeId) {
        for (Wearable wearable : wearableMap.values()) {
            if (wearable.getArmorTypeId() == armorTypeId && wearable.getSlotTypeId() == slotTypeId) {
                return Optional.of(wearable);
            }
        }
        return Optional.empty();
    }

    private void loadCharacters() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM characters;");
            connection.commit();
            characterList = new ArrayList<>();
            while (resultSet.next()) {
                Character character = new Character();
                character.setRace(Race.getById(resultSet.getInt("char_race_id")));
                character.setGender(Gender.getById(resultSet.getInt("char_gender_id")));
                character.setModelId(resultSet.getInt("model_id"));
                character.setFactionId(resultSet.getInt("faction_id"));
                character.setReturnDistrictId(resultSet.getInt("default_return_district_id"));
                character.setResurrectionDistrictId(resultSet.getInt("default_resurrection_district_id"));
                characterList.add(character);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadItemBodyParts() throws GameServerException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item_body_parts WHERE model_id = ?;")) {
            for (Character character : characterList) {
                preparedStatement.setInt(1, character.getModelId());
                ResultSet resultSet = preparedStatement.executeQuery();
                connection.commit();
                BodyParts body = new BodyParts();
                while (resultSet.next()) {
                    int slotTypeId = resultSet.getInt("slot_type_id");
                    int itemId = resultSet.getInt("item_id");
                    switch (slotTypeId) {
                        case 23 -> body.setFace(itemId);
                        case 24 -> body.setHair(itemId);
                        case 25 -> body.setEyeLeft(itemId);
                        case 26 -> body.setEyeRight(itemId);
                        case 27 -> body.setTail(itemId);
                        case 28 -> body.setBody(itemId);
                        case 29 -> body.setBeard(itemId);
                    }
                    character.setBodyParts(body);
                }
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    private void loadCharacterEquipPacks() throws GameServerException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM character_equip_packs JOIN equip_pack_cloths ON character_equip_packs.newbie_cloth_pack_id = equip_pack_cloths.id JOIN equip_pack_weapons ON character_equip_packs.newbie_weapon_pack_id = equip_pack_weapons.id;");
            connection.commit();
            characterEquipPackList = new TreeMap<>();
            while (resultSet.next()) {
                CharacterEquipPack characterEquipPack = new CharacterEquipPack();
                characterEquipPack.setAbility(Ability.getById(resultSet.getInt("ability_id")));
                EquipClothPack equipClothPack = new EquipClothPack();
                equipClothPack.setHeadId(resultSet.getInt("headgear_id"));
                equipClothPack.setHeadGrade(itemGradeMap.get(resultSet.getInt("headgear_grade_id")));
                equipClothPack.setNeckId(resultSet.getInt("necklace_id"));
                equipClothPack.setNeckGrade(itemGradeMap.get(resultSet.getInt("necklace_grade_id")));
                equipClothPack.setChestId(resultSet.getInt("shirt_id"));
                equipClothPack.setChestGrade(itemGradeMap.get(resultSet.getInt("shirt_grade_id")));
                equipClothPack.setWaistId(resultSet.getInt("belt_id"));
                equipClothPack.setWaistGrade(itemGradeMap.get(resultSet.getInt("belt_grade_id")));
                equipClothPack.setLegsId(resultSet.getInt("pants_id"));
                equipClothPack.setLegsGrade(itemGradeMap.get(resultSet.getInt("pants_grade_id")));
                equipClothPack.setHandsId(resultSet.getInt("glove_id"));
                equipClothPack.setHandsGrade(itemGradeMap.get(resultSet.getInt("glove_grade_id")));
                equipClothPack.setFeetId(resultSet.getInt("shoes_id"));
                equipClothPack.setFeetGrade(itemGradeMap.get(resultSet.getInt("shoes_grade_id")));
                equipClothPack.setArmsId(resultSet.getInt("bracelet_id"));
                equipClothPack.setArmsGrade(itemGradeMap.get(resultSet.getInt("bracelet_grade_id")));
                equipClothPack.setBackId(resultSet.getInt("back_id"));
                equipClothPack.setBackGrade(itemGradeMap.get(resultSet.getInt("back_grade_id")));
                equipClothPack.setCosplayId(resultSet.getInt("cosplay_id"));
                equipClothPack.setCosplayGrade(itemGradeMap.get(resultSet.getInt("cosplay_grade_id")));
                equipClothPack.setUndershirtId(resultSet.getInt("undershirt_id"));
                equipClothPack.setUndershirtGrade(itemGradeMap.get(resultSet.getInt("undershirt_grade_id")));
                equipClothPack.setUnderpantsId(resultSet.getInt("underpants_id"));
                equipClothPack.setUnderpantsGrade(itemGradeMap.get(resultSet.getInt("underpants_grade_id")));
                characterEquipPack.setEquipClothPack(equipClothPack);
                EquipWeaponPack equipWeaponPack = new EquipWeaponPack();
                equipWeaponPack.setMainHandId(resultSet.getInt("mainhand_id"));
                equipWeaponPack.setMainHandGrade(itemGradeMap.get(resultSet.getInt("mainhand_grade_id")));
                equipWeaponPack.setOffHandId(resultSet.getInt("offhand_id"));
                equipWeaponPack.setOffHandGrade(itemGradeMap.get(resultSet.getInt("offhand_grade_id")));
                equipWeaponPack.setRangedId(resultSet.getInt("ranged_id"));
                equipWeaponPack.setRangedGrade(itemGradeMap.get(resultSet.getInt("ranged_grade_id")));
                equipWeaponPack.setMusicalId(resultSet.getInt("musical_id"));
                equipWeaponPack.setMusicalGrade(itemGradeMap.get(resultSet.getInt("musical_grade_id")));
                characterEquipPack.setEquipWeaponPack(equipWeaponPack);
                characterEquipPackList.put(characterEquipPack.getAbility(), characterEquipPack);
            }
        } catch (SQLException e) {
            throw new GameServerException(e);
        }
    }

    @Override
    public ItemConfig getItemConfig() {
        return itemConfig;
    }

    @Override
    public Map<Integer, ItemGrade> getItemGradeMap() {
        return itemGradeMap;
    }

    @Override
    public Map<Integer, Holdable> getHoldableMap() {
        return holdableMap;
    }

    @Override
    public Map<Integer, Wearable> getWearableMap() {
        return wearableMap;
    }

    @Override
    public Map<Integer, WearableKind> getWearableKindMap() {
        return wearableKindMap;
    }

    @Override
    public Map<Integer, WearableSlot> getWearableSlotMap() {
        return wearableSlotMap;
    }

    @Override
    public Map<Integer, EquipItemAttributeModifier> getEquipItemAttributeModifierMap() {
        return equipItemAttributeModifierMap;
    }

    @Override
    public Map<Integer, ItemTemplate> getItemsMap() {
        return itemsMap;
    }

    @Override
    public Map<ItemGrade, GradeDistributionItem> getGradeDistributionItemMap() {
        return gradeDistributionItemMap;
    }

    @Override
    public Map<Integer, SpawnDoodadItem> getSpawnDoodadItemMap() {
        return spawnDoodadItemMap;
    }

    @Override
    public Map<Integer, ItemProcTemplate> getItemProcMap() {
        return itemProcMap;
    }

    @Override
    public List<Character> getCharacterList() {
        return characterList;
    }

    @Override
    public Map<Ability, CharacterEquipPack> getCharacterEquipPackList() {
        return characterEquipPackList;
    }
}
