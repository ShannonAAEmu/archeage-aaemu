package com.aaemu.game.service.config;


import com.aaemu.game.service.model.Account;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GameServerConfig {

    @Bean
    public Map<Channel, Account> accountMap() {
        return new ConcurrentHashMap<>();
    }
}
