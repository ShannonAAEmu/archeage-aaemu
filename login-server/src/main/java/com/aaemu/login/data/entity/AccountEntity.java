package com.aaemu.login.data.entity;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class AccountEntity {
    private int id;
    private boolean isDev;
    private boolean active;
}
