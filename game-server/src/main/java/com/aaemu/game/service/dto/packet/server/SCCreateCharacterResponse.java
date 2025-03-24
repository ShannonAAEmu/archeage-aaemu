package com.aaemu.game.service.dto.packet.server;

import com.aaemu.game.repository.dto.character.template.EquipClothPack;
import com.aaemu.game.repository.dto.character.template.EquipWeaponPack;
import com.aaemu.game.repository.dto.item.template.ItemTemplate;
import com.aaemu.game.service.ItemService;
import com.aaemu.game.service.ZoneService;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.enums.item.SlotType;
import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.enums.unit.Ability;
import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.FractionType;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import com.aaemu.game.service.exception.GameServerException;
import com.aaemu.game.service.model.Account;
import com.aaemu.game.service.util.ByteBufUtil;
import com.aaemu.game.service.util.PointUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Shannon
 */
@Data
public class SCCreateCharacterResponse {
    private final CSCreateCharacter newCharacter;
    private final Map<Channel, Account> accountMap;
    private final Race race;
    private final Gender gender;
    private final Ability ability_1;
    private boolean addStartupClothEquip;
    private boolean addStartupWeaponEquip;
    private int startLevel;
    private int startMoney;
    private EquipClothPack clothPack;
    private ZoneService zoneService;
    private ItemService itemService;

    public SCCreateCharacterResponse(CSCreateCharacter newCharacter, Map<Channel, Account> accountMap) {
        this.newCharacter = newCharacter;
        this.accountMap = accountMap;
        this.race = newCharacter.getRace();
        this.gender = newCharacter.getGender();
        this.ability_1 = newCharacter.getAbility_1();
    }

    public ByteBuf build(ByteBufUtil byteBufUtil) {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeLevel(PacketLevel._1, byteBuf);
        byteBufUtil.writeOpcode(ServerPacket.SC_CREATE_CHARACTER_RESPONSE, byteBuf);

        byteBufUtil.writeInt(1, byteBuf);   // id   // TODO generate id
        byteBufUtil.writeString(StringUtils.capitalize(newCharacter.getName()), byteBuf);
        byteBufUtil.writeByte(newCharacter.getRace().getRace(), byteBuf);
        byteBufUtil.writeByte(newCharacter.getGender().getGender(), byteBuf);
        byteBufUtil.writeByte(startLevel, byteBuf);
        byteBufUtil.writeInt(100, byteBuf);  // health  // TODO get start health
        byteBufUtil.writeInt(100, byteBuf);  // mana    // TODO get start mana
        byteBufUtil.writeInt(zoneService.getStartingZoneId(newCharacter.getRace(), newCharacter.getGender()), byteBuf);  // zid
        byteBufUtil.writeInt(FractionType.getByRace(race).getType(), byteBuf); // type (Fraction)
        byteBufUtil.writeInt(0, byteBuf);    // type (? Expedition)
        byteBufUtil.writeInt(0, byteBuf);    // family

        addStartupClothEquip(byteBufUtil, byteBuf, 1);
        addStartupWeaponEquip(byteBufUtil, byteBuf);
        initBodyParts(byteBufUtil, byteBuf);
        addStartupClothEquip(byteBufUtil, byteBuf, 2);

        byteBufUtil.writeByte(ability_1.getAbility(), byteBuf); // ability_1
        byteBufUtil.writeByte(0, byteBuf);  // ability_2
        byteBufUtil.writeByte(0, byteBuf);  // ability_3

        byteBufUtil.writeLong(PointUtil.convertX(15578.042), byteBuf); // x  00 00 00 00 34 D3 CA 03    // TODO get start pos X
        byteBufUtil.writeLong(PointUtil.convertY(15382.122), byteBuf); // y  00 00 00 00 98 A1 BD 03    // TODO get start pos Y
        byteBufUtil.writeFloat(126.484f, byteBuf); // z   F4 3D 03 43   // TODO get start pos Z
        byteBufUtil.write(newCharacter.getHeadDetails().build(), byteBuf);
        byteBufUtil.writeShort((short) 50, byteBuf);   // laborPower    // TODO get labor power
        byteBufUtil.writeLong(System.currentTimeMillis(), byteBuf);   // lastLaborPowerModified
        byteBufUtil.writeShort((short) 0, byteBuf);    // deadCount
        byteBufUtil.writeLong(0, byteBuf);   // deadTime
        byteBufUtil.writeInt(0, byteBuf);    // rezWaitDuration
        byteBufUtil.writeLong(0, byteBuf);   // rezTime
        byteBufUtil.writeInt(0, byteBuf);    // rezPenaltyDuration
        byteBufUtil.writeLong(0, byteBuf);   // lastWorldLeaveTime
        byteBufUtil.writeLong(startMoney, byteBuf);    // moneyAmount
        byteBufUtil.writeLong(0, byteBuf);    // moneyAmount ?
        byteBufUtil.writeShort((short) 0, byteBuf);    // crimePoint
        byteBufUtil.writeInt(0, byteBuf);    // crimeRecord
        byteBufUtil.writeShort((short) 0, byteBuf);    // crimeScore
        byteBufUtil.writeLong(0, byteBuf);   // deleteRequestedTime
        byteBufUtil.writeLong(0, byteBuf);   // transferRequestedTime
        byteBufUtil.writeLong(0, byteBuf);    // deleteDelay
        byteBufUtil.writeInt(0, byteBuf);    // consumedLp
        return byteBuf;
    }

    private void addStartupClothEquip(ByteBufUtil byteBufUtil, ByteBuf byteBuf, int part) {
        if (!addStartupClothEquip) {
            writeEmptyClothPack(byteBufUtil, byteBuf, part);
            return;
        }
        Optional<EquipClothPack> equipClothPack = itemService.getClothEquipPack(ability_1);
        if (equipClothPack.isEmpty()) {
            writeEmptyClothPack(byteBufUtil, byteBuf, part);
            return;
        }
        if (Objects.isNull(clothPack)) clothPack = equipClothPack.get();
        if (part == 1) {
            addEquip(itemService.createItem(clothPack.getHeadId(), clothPack.getHeadGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);// HEAD
            addEquip(itemService.createItem(clothPack.getNeckId(), clothPack.getNeckGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);// NECK
            addEquip(itemService.createItem(clothPack.getChestId(), clothPack.getChestGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // CHEST byteBufUtil.writeHex("5B5B000000000001000000000000010000000100000000550000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
            addEquip(itemService.createItem(clothPack.getWaistId(), clothPack.getWaistGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // WAIST
            addEquip(itemService.createItem(clothPack.getLegsId(), clothPack.getLegsGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // LEGS  byteBufUtil.writeHex("5C5B000001000001000000000000010000000100000000460000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
            addEquip(itemService.createItem(clothPack.getHandsId(), clothPack.getHandsGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // HANDS
            addEquip(itemService.createItem(clothPack.getFeetId(), clothPack.getFeetGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // FEET  byteBufUtil.writeHex("5E5B000002000001000000000000010000000100000000230000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
            addEquip(itemService.createItem(clothPack.getArmsId(), clothPack.getArmsGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // ARMS
            addEquip(itemService.createItem(clothPack.getBackId(), clothPack.getBackGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // BACK
            addEquip(itemService.createItem(0, itemService.getItemGrade(0), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // EAR_1
            addEquip(itemService.createItem(0, itemService.getItemGrade(0), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // EAR_2
            addEquip(itemService.createItem(0, itemService.getItemGrade(0), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // FINGER_1
            addEquip(itemService.createItem(0, itemService.getItemGrade(0), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // FINGER_2
            addEquip(itemService.createItem(clothPack.getUndershirtId(), clothPack.getUndershirtGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // UNDERSHIRT
            addEquip(itemService.createItem(clothPack.getUnderpantsId(), clothPack.getUnderpantsGrade(), SlotType.EQUIPMENT), byteBufUtil, byteBuf);  // UNDERPANTS
        } else {
            if (Objects.isNull(clothPack)) {
                byteBufUtil.writeInt(0, byteBuf);    // BACKPACK
                byteBufUtil.writeInt(0, byteBuf);    // COSPLAY
            } else {
                byteBufUtil.writeInt(0, byteBuf);   // BACKPACK ?   // TODO get start BACKPACK
                byteBufUtil.writeInt(0, byteBuf);   // clothPack.getCosplayId() // TODO get start COSPLAY
            }
        }
    }

    private void addStartupWeaponEquip(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        if (!addStartupWeaponEquip) {
            writeEmptyWeaponPack(byteBufUtil, byteBuf);
            return;
        }
        Optional<EquipWeaponPack> weaponEquipPack = itemService.getWeaponEquipPack(ability_1);
        if (weaponEquipPack.isEmpty()) {
            writeEmptyWeaponPack(byteBufUtil, byteBuf);
            return;
        }
        byteBufUtil.writeInt(0, byteBuf);  // MAIN_HAND     byteBufUtil.writeHex("D617000003000001000000000000010000000100000000910000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
        byteBufUtil.writeInt(0, byteBuf);  // OFF_HAND
        byteBufUtil.writeInt(0, byteBuf);  // RANGED        byteBufUtil.writeHex("EF17000004000001000000000000010000000100000000820000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
        byteBufUtil.writeInt(0, byteBuf);  // MUSICAL       byteBufUtil.writeHex("3A18000005000001000000000000010000000100000000820000000000000000000000000000761CB46700000000000000000000000001", byteBuf);
    }

    private void addEquip(ItemTemplate itemTemplate, ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        if (itemTemplate.getItemId() == 0) {
            byteBufUtil.writeInt(0, byteBuf);
        } else {
            byteBufUtil.writeInt(0, byteBuf);   // TODO create equip
        }
    }

    private void initBodyParts(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        Optional<BodyParts> characterBody = itemService.getCharacterBody(newCharacter.getRace(), newCharacter.getGender());
        if (characterBody.isEmpty()) throw new GameServerException("Invalid character race or gender");
        BodyParts bodyParts = characterBody.get();
        byteBufUtil.writeInt(newCharacter.getBodyParts().getFace() == 0 ? bodyParts.getFace() : newCharacter.getBodyParts().getFace(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getHair() == 0 ? bodyParts.getHair() : newCharacter.getBodyParts().getHair(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getEyeLeft() == 0 ? bodyParts.getEyeLeft() : newCharacter.getBodyParts().getEyeLeft(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getEyeRight() == 0 ? bodyParts.getEyeRight() : newCharacter.getBodyParts().getEyeRight(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getTail() == 0 ? bodyParts.getTail() : newCharacter.getBodyParts().getTail(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getBody() == 0 ? bodyParts.getBody() : newCharacter.getBodyParts().getBody(), byteBuf);
        byteBufUtil.writeInt(newCharacter.getBodyParts().getBeard() == 0 ? bodyParts.getBeard() : newCharacter.getBodyParts().getBeard(), byteBuf);
    }

    private void writeEmptyClothPack(ByteBufUtil byteBufUtil, ByteBuf byteBuf, int part) {
        if (part == 1) {
            for (int i = 0; i < 15; i++) {
                byteBufUtil.writeInt(0, byteBuf);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                byteBufUtil.writeInt(0, byteBuf);
            }
        }
    }

    private void writeEmptyWeaponPack(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        for (int i = 0; i < 4; i++) {
            byteBufUtil.writeInt(0, byteBuf);
        }
    }
}
