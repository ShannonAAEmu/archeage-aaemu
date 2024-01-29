package com.aaemu.login.service.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuthAccount {
    private Long id;
    private String name;
    private String password;
    private Timestamp lastLogin;
    private Timestamp updateAt;
    private Timestamp createAt;
}
