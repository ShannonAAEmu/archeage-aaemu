package com.aaemu.stream.service.impl;

import com.aaemu.stream.service.GameServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class GameServerServiceImpl implements GameServerService {
    private final RestClient restClient;

    @Value("${stream_server.game_server.url}")
    private String gameServerUrl;

    @Override
    public void join(int accountId) {
        restClient.get()
                .uri(gameServerUrl + "join/" + accountId)
                .retrieve()
                .toBodilessEntity();
    }
}
