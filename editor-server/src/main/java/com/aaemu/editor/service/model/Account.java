package com.aaemu.editor.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account {
    private final String name;
    private long id;
}
