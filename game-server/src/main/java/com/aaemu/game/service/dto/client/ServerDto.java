package com.aaemu.game.service.dto.client;

import lombok.Data;

import java.util.List;

@Data
public class ServerDto {
    private byte id;
    private String name;
    private boolean isAvailable;
    private byte con;        // Congestion: 0 - blue, 1 - yellow, 2 - red
    private List<Boolean> rCon; // Race congestion

    public void setId(int id) {
        this.id = (byte) id;
    }

    public void setCon(int con) {
        this.con = (byte) con;
    }

}
