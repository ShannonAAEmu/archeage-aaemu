package com.aaemu.login.service.impl;

import com.aaemu.login.service.GameServerService;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.model.Account;
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

    @Value("${login_server.game_server.url}")
    private String gameServerUrl;

    @Override
    public ServerDto getServerInfo(Account account) {
        return restClient.get()
                .uri(gameServerUrl + "info/" + account.getId())
                .retrieve()
                .body(ServerDto.class);
    }

    @Override
    public QueueStatusDto getQueueStatus(int worldId, Account account) {
        return restClient.get()
                .uri(gameServerUrl + "queue/" + account.getId())
                .retrieve()
                .body(QueueStatusDto.class);
    }
}
