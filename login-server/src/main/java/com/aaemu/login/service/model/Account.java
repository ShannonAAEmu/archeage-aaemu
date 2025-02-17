package com.aaemu.login.service.model;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.List;

@Data
public class Account {
    private final Channel channel;
    private int id;
    private int cookie;
    private boolean isDev;
    private String name;
    private String password;
    private List<Integer> challengeH1;
    private List<Integer> challengeH2;

    public Account(Channel channel) {
        this.channel = channel;
    }
}
