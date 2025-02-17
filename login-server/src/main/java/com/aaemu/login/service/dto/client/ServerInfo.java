package com.aaemu.login.service.dto.client;

import com.aaemu.login.service.enums.ServerAvailability;
import com.aaemu.login.service.enums.ServerCongestion;
import com.aaemu.login.service.model.ServerRaceCongestion;
import lombok.Data;

import java.util.List;

@Data
public class ServerInfo {
    private byte id;
    private String name;
    private ServerAvailability available;
    private ServerCongestion con;
    private ServerRaceCongestion rCon;
    private List<Character> characters;

    public ServerAvailability isAvailable() {
        return this.available;
    }
}
