package com.aaemu.game.service.model;

import com.aaemu.game.service.dto.packet.server.BroadcastVisualOption;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account {
    private final long id;
    private String name;
    private BroadcastVisualOption broadcastVisualOption;
}
