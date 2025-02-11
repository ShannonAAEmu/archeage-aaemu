package com.aaemu.stream.service.enums;

import com.aaemu.stream.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    TC_JOIN_RESPONSE("0001", "0100");

    private final String opcode;
    private final String rawOpcode;

    public static ServerPacket getByRawOpcode(String rawOpcode) {
        for (ServerPacket packet : values()) {
            if (packet.getRawOpcode().equalsIgnoreCase(rawOpcode)) {
                return packet;
            }
        }
        throw new PacketException("Unknown server packet raw opcode: " + rawOpcode);
    }
}
