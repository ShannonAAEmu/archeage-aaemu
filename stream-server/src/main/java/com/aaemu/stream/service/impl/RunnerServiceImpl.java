package com.aaemu.stream.service.impl;


import com.aaemu.stream.service.RunnerService;
import com.aaemu.stream.service.netty.StreamServer;
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
    private final StreamServer streamServer;

    @PostConstruct
    @Override
    public void start() {
        streamServer.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        streamServer.stop();
    }
}
