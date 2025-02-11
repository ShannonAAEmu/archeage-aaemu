package com.aaemu.game.service.dto.client;

import com.aaemu.game.service.enums.ServerAvailability;
import com.aaemu.game.service.enums.ServerCongestion;
import com.aaemu.game.service.model.ServerRaceCongestion;
import lombok.Data;

import java.util.List;

@Data
public class ServerDto {
    private byte id;
    private String name;
    private ServerAvailability available;
    private ServerCongestion con;
    private ServerRaceCongestion rCon;
    private List<CharacterDto> characters;

    public void setId(int id) {
        this.id = (byte) id;
    }

    public ServerAvailability isAvailable() {
        return this.available;
    }
}
