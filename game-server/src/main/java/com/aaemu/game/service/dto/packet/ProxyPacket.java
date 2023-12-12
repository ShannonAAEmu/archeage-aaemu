package com.aaemu.game.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProxyPacket {
    CHANGE_STATE("0"),
    FINISH_STATE("1"),
    PING("12"),
    PONG("13"),
    SET_GAME_TYPE("F");

    private final String opcode;

    public static ProxyPacket getByOpcode(String opcode) {
        for (ProxyPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new RuntimeException(String.format("Unknown proxy packet opcode: %s", opcode));
    }
}
