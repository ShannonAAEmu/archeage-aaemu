package com.aaemu.game.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StreamClientPacket {
    TC_JOIN_RESPONSE("1");

    private final String opcode;

    public static StreamClientPacket getByOpcode(String opcode) {
        for (StreamClientPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new RuntimeException(String.format("Unknown stream server packet opcode: %s", opcode));
    }
}
