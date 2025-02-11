package com.aaemu.login.service.impl;

import com.aaemu.login.service.RunnerService;
import com.aaemu.login.service.netty.LoginServer;
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
    private final LoginServer loginServer;

    @PostConstruct
    @Override
    public void start() {
        loginServer.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        loginServer.stop();
    }
}
