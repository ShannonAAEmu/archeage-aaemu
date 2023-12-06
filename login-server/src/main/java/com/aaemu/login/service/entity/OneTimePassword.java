package com.aaemu.login.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OneTimePassword {
    private int count;
    private String password;
}
