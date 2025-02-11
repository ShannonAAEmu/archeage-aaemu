package com.aaemu.login.service.dto.client;

import lombok.Data;

@Data
public class QueueStatusDto {
    private String ip;
    private int port;
    private int turnCount;
    private int totalCount;
}
