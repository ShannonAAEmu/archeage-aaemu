package com.aaemu.login;

import com.aaemu.login.service.model.TempPassword;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LoginServerConfig {

    @Bean
    public ByteBufUtil byteBufUtil() {
        return new ByteBufUtil(true, StandardCharsets.US_ASCII);
    }

    @Bean
    public WebClient jsonWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public Map<Channel, String> accountMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, TempPassword> otpMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, TempPassword> pcCertMap() {
        return new HashMap<>();
    }

    @Bean
    public Map<Channel, Integer> cookieMap() {
        return new HashMap<>();
    }
}
