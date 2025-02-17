package com.aaemu.login.service.dto.client;

import lombok.Data;

@Data
public class QueueStatus {
    private String ip;
    private int port;
    private short turnCount;
    private short totalCount;
}
