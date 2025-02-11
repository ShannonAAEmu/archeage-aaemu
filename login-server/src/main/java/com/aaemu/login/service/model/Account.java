package com.aaemu.login.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account {
    private long id;
    private final String name;
    private String password;
    private String challengeH1;
    private String challengeH2;
}
