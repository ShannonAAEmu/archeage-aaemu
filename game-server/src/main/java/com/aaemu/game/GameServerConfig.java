package com.aaemu.game;


import com.aaemu.game.util.ByteBufUtil;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class GameServerConfig {

    @Bean
    public ByteBufUtil byteBufUtil() {
        return new ByteBufUtil(true, StandardCharsets.US_ASCII);
    }

    @Bean
    public Map<Channel, String> accountMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, Integer> cookieMap() {
        return new HashMap<>();
    }
}
