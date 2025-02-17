package com.aaemu.game.service.dto.client;

import com.aaemu.game.service.enums.server.ServerAvailability;
import com.aaemu.game.service.enums.server.ServerCongestion;
import com.aaemu.game.service.model.ServerRaceCongestion;
import lombok.Data;

import java.util.List;

@Data
public class Server {
    private byte id;
    private String name;
    private ServerAvailability available;
    private ServerCongestion con;
    private ServerRaceCongestion rCon;
    private List<Character> characters;

    public void setId(int id) {
        this.id = (byte) id;
    }

    public ServerAvailability isAvailable() {
        return this.available;
    }
}
