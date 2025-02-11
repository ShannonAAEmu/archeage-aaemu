package com.aaemu.zone.service.impl;

import com.aaemu.zone.service.RunnerService;
import com.aaemu.zone.service.netty.ZoneServer;
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
    private final ZoneServer zoneServer;

    @PostConstruct
    @Override
    public void start() {
        zoneServer.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        zoneServer.stop();
    }
}
