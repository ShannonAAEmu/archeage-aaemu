package com.aaemu.game.controller;

import com.aaemu.game.service.annotation.TestData;
import com.aaemu.game.service.dto.client.AddressDto;
import com.aaemu.game.service.dto.client.CharacterDto;
import com.aaemu.game.service.dto.client.LoginAccountDto;
import com.aaemu.game.service.dto.client.QueueStatusDto;
import com.aaemu.game.service.dto.client.ServerDto;
import com.aaemu.game.service.exception.GameServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@Slf4j
public class ServerController {

    @Value("${game.port}")
    private int gamePort;

    @Value("${game.id}")
    private int serverId;

    @GetMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
    public AddressDto getIp() throws UnknownHostException {
        AddressDto addressDto = new AddressDto();
        addressDto.setIp(InetAddress.getLocalHost().getHostAddress());
        addressDto.setPort(gamePort);
        return addressDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ServerDto> list() {
        List<ServerDto> serverList = new ArrayList<>();
        serverList.add(getTempServer());    // TODO server logic
        return serverList;
    }

    @PostMapping(value = "/account/characters", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CharacterDto> characters(@RequestBody LoginAccountDto loginAccountDto) {
        log.info("Request characters list from account: {}", loginAccountDto.getName());
        // TODO character logic
        return new ArrayList<>();
    }

    @GetMapping(value = "/{id}/queue", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hasQueue(@PathVariable int id) {
        if (serverId == id) {
            // TODO queue logic
            return String.valueOf(true);
        }
        throw new GameServerException("Invalid server id");
    }

    @PostMapping(value = "/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public QueueStatusDto getQueueStatus(@PathVariable int id, @RequestBody LoginAccountDto loginAccountDto) {
        if (serverId == id) {
            log.info("Request queue status account: {}", loginAccountDto.getName());
            // TODO queue logic
            QueueStatusDto queueStatusDto = new QueueStatusDto();
            queueStatusDto.setTurnCount(2);
            queueStatusDto.setTotalCount(1000);
            return queueStatusDto;
        }
        throw new GameServerException("Invalid server id");
    }

    @TestData
    private ServerDto getTempServer() {
        ServerDto server = new ServerDto();
        server.setId(1);
        server.setName("AAEmu");
        server.setAvailable(true);
        if (server.isAvailable()) {
            server.setCon(0);
            List<Boolean> rCon = new ArrayList<>();
            rCon.add(false); // warborn
            rCon.add(false); // nuian
            rCon.add(false); // returned
            rCon.add(false); // fairy
            rCon.add(false); // elf
            rCon.add(false); // hariharan
            rCon.add(false); // ferre
            rCon.add(false); // dwarf
            rCon.add(false); // none
            server.setRCon(rCon);
        }
        return server;
    }

//    @TestData
//    private Character getTempCharacter() {
//        Character character = new Character();
//        character.setAccountId(1);
//        character.setWorldId(1);
//        character.setCharId(1);
//        character.setName("Shannon");
//        character.setCharRace(1);
//        character.setCharGender(1);
//        character.setGuid("1234567890123456");
//        character.setV(0);
//        return character;
//    }
}
