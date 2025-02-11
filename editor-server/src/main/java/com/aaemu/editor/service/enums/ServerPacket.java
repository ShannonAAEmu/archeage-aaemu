package com.aaemu.editor.service.enums;

import com.aaemu.editor.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    EC_LOGIN_RESPONSE("0001", "0100"),
    EC_PING("0007", "0700");

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
