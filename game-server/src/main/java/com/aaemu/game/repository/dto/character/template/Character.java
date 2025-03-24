package com.aaemu.game.repository.dto.character.template;

import com.aaemu.game.service.enums.unit.BodyParts;
import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class Character {
    private Race race;
    private Gender gender;
    private int modelId;
    private int factionId;
    private int startingZoneId;
    private int returnDistrictId;
    private int resurrectionDistrictId;
    private BodyParts bodyParts;
}
