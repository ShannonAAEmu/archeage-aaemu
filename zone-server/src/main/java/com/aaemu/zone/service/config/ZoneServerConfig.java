package com.aaemu.zone.service.config;


import com.aaemu.zone.service.model.Account;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ZoneServerConfig {

    @Bean
    public Map<Channel, Account> accountMap() {
        return new HashMap<>();
    }
}
