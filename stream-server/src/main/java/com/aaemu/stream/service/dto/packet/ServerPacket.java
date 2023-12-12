package com.aaemu.stream.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    TC_JOIN_RESPONSE("1");

    private final String opcode;

    public static ServerPacket getByOpcode(String opcode) {
        for (ServerPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new RuntimeException(String.format("Unknown server packet opcode: %s", opcode));
    }
}
