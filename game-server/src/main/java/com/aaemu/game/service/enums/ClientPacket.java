package com.aaemu.game.service.enums;

import com.aaemu.game.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    X2_ENTER_WORLD("0000", "0000"),
    CS_LEAVE_WORLD("0001", "0100"),
    CS_LIST_CHARACTER("0019", "1900"),
    CS_BROADCAST_VISUAL_OPTION("0101", "0101"),
    CS_REFRESH_IN_CHARACTER_LIST("001A", "1A00"),
    CS_CREATE_CHARACTER("001B", "1B00");

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
