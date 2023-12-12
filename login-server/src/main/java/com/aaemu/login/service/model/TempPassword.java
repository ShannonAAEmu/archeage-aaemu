package com.aaemu.login.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TempPassword {
    private int count;
    private String password;
}
