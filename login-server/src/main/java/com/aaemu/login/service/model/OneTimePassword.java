package com.aaemu.login.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
@AllArgsConstructor
public class OneTimePassword {
    private int count;
    private String number;
}
