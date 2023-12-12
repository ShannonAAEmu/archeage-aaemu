package com.aaemu.game.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    X2_ENTER_WORLD_RESPONSE("0"),
    SC_INITIAL_CONFIG("5"),
    SC_CHAT_SPAM_DELAY("B0"),
    SC_ACCOUNT_INFO("198"),
    SC_CHARACTER_LIST("2D");

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
