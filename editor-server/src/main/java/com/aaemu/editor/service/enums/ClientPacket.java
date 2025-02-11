package com.aaemu.editor.service.enums;

import com.aaemu.editor.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CE_LOGIN("0001", "0100"),
    EC_PONG("0007", "0700");


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
