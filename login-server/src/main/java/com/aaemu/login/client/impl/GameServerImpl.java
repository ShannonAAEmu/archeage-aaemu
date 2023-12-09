package com.aaemu.login.client.impl;

import com.aaemu.login.client.GameServer;
import com.aaemu.login.service.dto.client.AddressDto;
import com.aaemu.login.service.dto.client.CharacterDto;
import com.aaemu.login.service.dto.client.LoginAccountDto;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.exception.LoginServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServerImpl implements GameServer {
    private final RestClient restClient;

    @Value("${clients.game.url}")
    private String gameServerUrl;

    @Override
    public AddressDto getAddress() {
        AddressDto addressDto = restClient.get()
                .uri(String.format("%s/%s", gameServerUrl, "address"))
                .retrieve()
                .body(AddressDto.class);
        if (addressDto == null || addressDto.getIp() == null || addressDto.getIp().isBlank() || addressDto.getPort() <= 0) {
            throw new LoginServerException("Not found game server address");
        }
        return addressDto;
    }

    @Override
    public List<ServerDto> getServerList() {
        List<ServerDto> serverDtoList = restClient.get()
                .uri(String.format("%s/%s", gameServerUrl, "list"))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return (serverDtoList == null || serverDtoList.isEmpty()) ? new ArrayList<>(0) : serverDtoList;
    }

    @Override
    public List<CharacterDto> getCharacterList(LoginAccountDto loginAccountDto) {
        List<CharacterDto> characterDtoList = restClient.post()
                .uri(String.format("%s/%s/%s", gameServerUrl, "account", "characters"))
                .body(loginAccountDto)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return (characterDtoList == null || characterDtoList.isEmpty()) ? new ArrayList<>(0) : characterDtoList;
    }

    @Override
    public Boolean hasQueue(int worldId) {
        return Boolean.TRUE.toString().equals(restClient.get()
                .uri(String.format("%s/%d/%s", gameServerUrl, worldId, "queue"))
                .retrieve()
                .body(String.class));
    }

    @Override
    public QueueStatusDto getQueueStatus(int worldId, LoginAccountDto loginAccountDto) {
        QueueStatusDto queueStatusDto = restClient.post()
                .uri(String.format("%s/%d/%s", gameServerUrl, worldId, "status"))
                .body(loginAccountDto)
                .retrieve()
                .body(QueueStatusDto.class);
        if (queueStatusDto == null) {
            throw new LoginServerException("Not found game server address");
        }
        return queueStatusDto;
    }
}
