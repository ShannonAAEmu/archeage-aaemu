package com.aaemu.game.service.model;

import lombok.Data;

@Data
public class Character {
    private int id;
    private String name;
    private byte charRace;
    private byte charGender;
    private byte level;
    private int health;
    private int mana;
    private int zoneId; // zid
    private int type_1;   // factionId
    private int type_2;   // expeditionId
    private int family;   // familyId
    //TODO next params
}
