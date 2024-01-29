package com.aaemu.game.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GameAccount {
    private final Long id;
    private Long streamCookie;
    private BroadcastVisualOption broadcastVisualOption;
}
