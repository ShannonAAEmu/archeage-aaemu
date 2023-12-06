package com.aaemu.login.service.dto;

import lombok.Data;

@Data
public class CharacterDto {
    private int accountId;
    private byte worldId;
    private int charId;
    private String name;
    private byte charRace;
    private byte charGender;
    private String guid;
    private long v;         // ?

    public void setWorldId(int worldId) {
        this.worldId = (byte) worldId;
    }

    public void setCharRace(int charRace) {
        this.charRace = (byte) charRace;
    }

    public void setCharGender(int charGender) {
        this.charGender = (byte) charGender;
    }
}
