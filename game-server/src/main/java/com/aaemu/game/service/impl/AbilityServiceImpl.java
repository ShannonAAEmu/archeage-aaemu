package com.aaemu.game.service.impl;

import com.aaemu.game.repository.dto.character.CharacterEquipPack;
import com.aaemu.game.service.AbilityService;
import com.aaemu.game.service.SqliteService;
import com.aaemu.game.service.enums.unit.Ability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class AbilityServiceImpl implements AbilityService {
    private final SqliteService sqliteService;

    @Override
    public boolean supportForCreate(Ability ability) {
        for (Map.Entry<Ability, CharacterEquipPack> entry : sqliteService.getCharacterEquipPackList().entrySet()) {
            if (entry.getValue().getAbility().equals(ability)) {
                return true;
            }
        }
        return false;
    }
}
