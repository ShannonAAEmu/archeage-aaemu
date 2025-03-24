package com.aaemu.game.service;

import com.aaemu.game.service.enums.unit.Gender;
import com.aaemu.game.service.enums.unit.Race;

/**
 * @author Shannon
 */
public interface ZoneService {

    int getStartingZoneId(Race race, Gender gender);
}
