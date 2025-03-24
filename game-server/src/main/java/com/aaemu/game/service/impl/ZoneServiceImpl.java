package com.aaemu.game.service.impl;

import com.aaemu.game.repository.dto.character.template.Character;
import com.aaemu.game.service.SqliteService;
import com.aaemu.game.service.ZoneService;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import com.aaemu.game.service.exception.GameServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {
    private final SqliteService sqliteService;

    @Override
    public int getStartingZoneId(Race race, Gender gender) {
        List<Character> characterList = sqliteService.getCharacterList();
        for (Character character : characterList) {
            if (character.getRace().equals(race) && character.getGender().equals(gender)) {
                return character.getStartingZoneId();
            }
        }
        throw new GameServerException("Unknown starting zone for race: " + race + ", gender: " + gender);
    }
}
