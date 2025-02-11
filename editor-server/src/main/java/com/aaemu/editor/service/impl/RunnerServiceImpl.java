package com.aaemu.editor.service.impl;

import com.aaemu.editor.service.RunnerService;
import com.aaemu.editor.service.netty.EditorServer;
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
    private final EditorServer editorServer;

    @PostConstruct
    @Override
    public void start() {
        editorServer.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        editorServer.stop();
    }
}
