package com.aaemu.stream.client.impl;

import com.aaemu.stream.client.GameServer;
import com.aaemu.stream.service.dto.client.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class GameServerImpl implements GameServer {
    private final RestClient restClient;

    @Value("${clients.game.url}")
    private String gameServerUrl;

    @Override
    public void sendAuth(CharacterDto characterDto) {
        restClient.post()
                .uri(String.format("%s/%s/%s", gameServerUrl, "stream", "join"))
                .body(characterDto)
                .retrieve()
                .toBodilessEntity();
    }
}
