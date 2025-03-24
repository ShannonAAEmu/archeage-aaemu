package com.aaemu.login.service.model;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@Data
@RequiredArgsConstructor
public class Account {
    private final Channel channel;
    private int id;
    private int cookie;
    private boolean isDev;
    private boolean isActive;
    private String name;
    private String password;
}
