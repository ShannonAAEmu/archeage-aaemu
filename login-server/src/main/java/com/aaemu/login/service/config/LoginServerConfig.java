package com.aaemu.login.service.config;

import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.model.OneTimePassword;
import io.netty.channel.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shannon
 */
@Configuration
public class LoginServerConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public Map<Channel, Account> accountMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Channel, OneTimePassword> otpMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Channel, OneTimePassword> pcCertMap() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Channel, Short> queueMap() {
        return new ConcurrentHashMap<>();
    }
}
