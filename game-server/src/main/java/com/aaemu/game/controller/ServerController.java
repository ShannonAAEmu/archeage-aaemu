package com.aaemu.game.controller;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.annotation.TestData;
import com.aaemu.game.service.dto.client.AccountFutureSet;
import com.aaemu.game.service.dto.client.QueueStatus;
import com.aaemu.game.service.dto.client.Server;
import com.aaemu.game.service.enums.server.ServerAvailability;
import com.aaemu.game.service.enums.server.ServerCongestion;
import com.aaemu.game.service.model.ServerRaceCongestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author Shannon
 */
@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@Log4j2
public class ServerController {
    private final AuthService authService;

    @Value("${game_server.id}")
    private int serverId;

    @Value("${game_server.name}")
    private String serverName;

    @Value("${game_server.port}")
    private int gamePort;

    @GetMapping(value = "/login/info/future_set", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountFutureSet futureSet() {
        return authService.getAccountFutureSet();
    }

    @GetMapping(value = "/login/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Server info(@PathVariable int id) {
        log.info("Request server info from account: {}", id);
        // TODO server info logic
        return getTestServerInfo();
    }

    @GetMapping(value = "/login/queue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public QueueStatus queue(@PathVariable int id) throws UnknownHostException {
        log.info("Request queue info from account: {}", id);
        // TODO server queue logic
        return getTestQueueStatus();
    }

    @TestData
    private Server getTestServerInfo() {
        Server server = new Server();
        server.setId(serverId);
        server.setName(serverName);
        server.setAvailable(ServerAvailability.AVAILABLE);
        if (server.isAvailable().getStatus()) {
            server.setCon(ServerCongestion.BLUE);
            server.setRCon(new ServerRaceCongestion());
        }
        server.setCharacters(new ArrayList<>());
        return server;
    }

    private QueueStatus getTestQueueStatus() throws UnknownHostException {
        QueueStatus queueStatus = new QueueStatus();
        queueStatus.setTurnCount(0);
        queueStatus.setTotalCount(0);
        queueStatus.setIp(InetAddress.getLocalHost().getHostAddress());
        queueStatus.setPort(gamePort);
        return queueStatus;
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

    @GetMapping(value = "/stream/join/{id}")
    public ResponseEntity<Void> streamJoin(@PathVariable int id) {
        authService.changeState(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
