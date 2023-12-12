package com.aaemu.game.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StreamClientPacket {
    TC_JOIN_RESPONSE("1");

    private final String opcode;
}
