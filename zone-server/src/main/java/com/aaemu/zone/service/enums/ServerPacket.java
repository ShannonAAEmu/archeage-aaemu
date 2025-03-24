package com.aaemu.zone.service.enums;

import com.aaemu.zone.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    WZ_Join_Response("0000", "0000"),
    WZ_RUN_COMMAND("0001", "0100"), // Not used in emu
    WZ_SPAWNER_LIST("0005", "0500");

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
