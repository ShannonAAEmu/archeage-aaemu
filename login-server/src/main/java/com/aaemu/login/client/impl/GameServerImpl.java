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
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServerImpl implements GameServer {
    private final WebClient jsonWebClient;

    @Value("${clients.game.url}")
    private String gameServerUrl;

    @Override
    public AddressDto getAddress() {
        AddressDto addressDto = jsonWebClient.get()
                .uri(String.format("%s/%s", gameServerUrl, "address"))
                .retrieve()
                .bodyToMono(AddressDto.class)
                .block();
        if (addressDto == null || addressDto.getIp() == null || addressDto.getIp().isBlank() || addressDto.getPort() <= 0) {
            throw new LoginServerException("Not found game server address");
        }
        return addressDto;
    }

    @Override
    public List<ServerDto> getServerList() {
        List<ServerDto> serverDtoList = jsonWebClient.get()
                .uri(String.format("%s/%s", gameServerUrl, "list"))
                .retrieve()
                .bodyToFlux(ServerDto.class)
                .collectList()
                .block();
        return (serverDtoList == null || serverDtoList.isEmpty()) ? new ArrayList<>(0) : serverDtoList;
    }

    @Override
    public List<CharacterDto> getCharacterList(LoginAccountDto loginAccountDto) {
        List<CharacterDto> characterDtoList = jsonWebClient.post()
                .uri(String.format("%s/%s/%s", gameServerUrl, "account", "characters"))
                .bodyValue(loginAccountDto)
                .retrieve()
                .bodyToFlux(CharacterDto.class)
                .collectList()
                .block();
        return (characterDtoList == null || characterDtoList.isEmpty()) ? new ArrayList<>(0) : characterDtoList;
    }

    @Override
    public Boolean hasQueue(int worldId) {
        return Boolean.TRUE.toString().equals(jsonWebClient.get()
                .uri(String.format("%s/%d/%s", gameServerUrl, worldId, "queue"))
                .retrieve()
                .bodyToMono(String.class)
                .block());
    }

    @Override
    public QueueStatusDto getQueueStatus(int worldId, LoginAccountDto loginAccountDto) {
        QueueStatusDto queueStatusDto = jsonWebClient.post()
                .uri(String.format("%s/%d/%s", gameServerUrl, worldId, "status"))
                .bodyValue(loginAccountDto)
                .retrieve()
                .bodyToMono(QueueStatusDto.class)
                .block();
        if (queueStatusDto == null) {
            throw new LoginServerException("Not found game server address");
        }
        return queueStatusDto;
    }
}
