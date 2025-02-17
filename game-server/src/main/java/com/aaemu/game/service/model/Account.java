package com.aaemu.game.service.model;

import com.aaemu.game.service.dto.packet.server.BroadcastVisualOption;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;

@Data
@RequiredArgsConstructor
public class Account {
    private final int id;
    private final int cookie;
    private int zoneId;
    private String name;
    private BroadcastVisualOption broadcastVisualOption;
    private OffsetDateTime warnedSendTime;
}
