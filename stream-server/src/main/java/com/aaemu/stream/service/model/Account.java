package com.aaemu.stream.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account {
    private final int id;
    private String name;
}
