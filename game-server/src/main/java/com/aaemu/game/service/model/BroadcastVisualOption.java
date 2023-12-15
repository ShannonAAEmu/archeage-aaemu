package com.aaemu.game.service.model;

import lombok.Data;

import java.util.List;

@Data
public class BroadcastVisualOption {
    private int voptFlag;     // voptflag
    private List<Integer> stpList;  // stp
    private boolean helmet;
}
