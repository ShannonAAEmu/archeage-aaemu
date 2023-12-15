package com.aaemu.stream.service.enums;

import com.aaemu.stream.service.exception.PacketException;
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
        throw new PacketException(String.format("Unknown server packet opcode: %s", opcode));
    }
}
