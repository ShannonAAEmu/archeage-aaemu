package com.aaemu.game.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account {
    private final long id;
    private BroadcastVisualOption broadcastVisualOption;
}
