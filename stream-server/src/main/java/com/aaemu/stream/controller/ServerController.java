package com.aaemu.stream.controller;

import com.aaemu.stream.service.dto.client.AddressDto;
import com.aaemu.stream.service.exception.GameServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@Slf4j
public class ServerController {

    @Value("${server.port}")
    private int streamPort;

    @GetMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressDto getIp() {
        try {
            AddressDto addressDto = new AddressDto();
            addressDto.setIp(InetAddress.getLocalHost().getHostAddress());
            addressDto.setPort(streamPort);
            return addressDto;
        } catch (UnknownHostException e) {
            throw new GameServerException(e);
        }
    }
}
