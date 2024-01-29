package com.aaemu.stream.service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StreamAccount {
    private final Long id;
    private Long cookie;
}
