package com.aaemu.stream;


import com.aaemu.stream.util.ByteBufUtil;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class StreamServerConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public ByteBufUtil byteBufUtil() {
        return new ByteBufUtil(true, StandardCharsets.US_ASCII);
    }

    @Bean
    public Map<Channel, Long> accountMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, Integer> cookieMap() {
        return new HashMap<>();
    }
}
