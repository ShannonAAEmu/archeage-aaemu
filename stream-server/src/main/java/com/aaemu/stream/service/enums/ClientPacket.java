package com.aaemu.stream.service.enums;

import com.aaemu.stream.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CT_JOIN("0001", "0100");

    private final String opcode;
    private final String rawOpcode;

    public static ClientPacket getByRawOpcode(String rawOpcode) {
        for (ClientPacket packet : values()) {
            if (packet.getRawOpcode().equalsIgnoreCase(rawOpcode)) {
                return packet;
            }
        }
        throw new PacketException("Unknown client packet raw opcode: " + rawOpcode);
    }
}
