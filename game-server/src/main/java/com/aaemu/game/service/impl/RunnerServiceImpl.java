package com.aaemu.game.service.impl;

import com.aaemu.game.service.RunnerService;
import com.aaemu.game.service.netty.GameServer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class RunnerServiceImpl implements RunnerService {
    private final GameServer gameServer;

    @PostConstruct
    @Override
    public void start() {
        gameServer.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        gameServer.stop();
    }
}
