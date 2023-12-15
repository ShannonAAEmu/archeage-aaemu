package com.aaemu.login.service.model;

import lombok.Data;

@Data
public class Account {
    private String name;
    private long id;

    public Account(String name) {
        this.name = name;
    }

    public Account(long id) {
        this.id = id;
    }
}
