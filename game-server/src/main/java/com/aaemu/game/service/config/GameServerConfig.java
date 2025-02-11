package com.aaemu.game.service.config;


import com.aaemu.game.service.model.Account;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GameServerConfig {

    @Bean
    public Map<Channel, Account> accountMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, Integer> cookieMap() {
        return new HashMap<>();
    }
}
