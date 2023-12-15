package com.aaemu.stream.service.enums;

import com.aaemu.stream.service.exception.PacketException;
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
        throw new PacketException(String.format("Unknown client packet opcode: %s", opcode));
    }
}
