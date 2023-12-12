package com.aaemu.stream.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CT_JOIN("1");

    private final String opcode;

    public static ClientPacket getByOpcode(String opcode) {
        for (ClientPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new RuntimeException(String.format("Unknown client packet opcode: %s", opcode));
    }
}
