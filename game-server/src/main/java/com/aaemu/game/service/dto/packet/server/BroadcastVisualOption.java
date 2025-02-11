package com.aaemu.game.service.dto.packet.server;

import lombok.Data;

import java.util.List;

/**
 * @author Shannon
 */
@Data
public class BroadcastVisualOption {
    private byte voptFlag;     // voptflag
    private List<Byte> stpList;  // stp
    private boolean helmet;
}