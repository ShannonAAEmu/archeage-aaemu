package com.aaemu.game.service.dto.client;

import lombok.Data;

@Data
public class QueueStatus {
    private String ip;
    private int port;
    private int turnCount;
    private int totalCount;
}
