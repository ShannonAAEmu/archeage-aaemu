package com.aaemu.game.client.impl;


import com.aaemu.game.client.StreamServer;
import com.aaemu.game.service.dto.client.AddressDto;
import com.aaemu.game.service.exception.GameServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class StreamServerImpl implements StreamServer {
    private final RestClient restClient;

    @Value("${clients.stream.url}")
    private String streamServerUrl;

    @Override
    public AddressDto getStreamAddress() {
        return restClient.get()
                .uri(String.format("%s/%s", streamServerUrl, "address"))
                .retrieve()
                .body(AddressDto.class);
    }
}
