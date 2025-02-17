package com.aaemu.game.service.enums.packet;

import com.aaemu.game.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    X2_ENTER_WORLD_RESPONSE("0000", "0000"),
    SC_RECONNECT_AUTH("0001", "0100"),
    SC_INITIAL_CONFIG("0005", "0500"),
    SC_CHARACTER_LIST("002D", "2D00"),
    SC_CHAT_SPAM_DELAY("00B0", "B000"),
    SC_ACCOUNT_WARNED("0178", "7801"),
    SC_ACCOUNT_INFO("0198", "9801"),
    SC_REFRESH_IN_CHARACTER_LIST("01A2", "A201"),
    SC_CHARACTER_CREATION_FAILED("002C", "2C00"),
    SC_CREATE_CHARACTER_RESPONSE("0027", "2700");

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
