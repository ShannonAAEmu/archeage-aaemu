package com.aaemu.game.service.enums.packet;

import com.aaemu.game.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum ProxyPacket {
    PING("0012", "1200"),   // Client
    PONG("0013", "1300"),   // Server
    CHANGE_STATE("0000", "0000"),   // Server
    FINISH_STATE("0001", "0100"),   // Client
    SET_GAME_TYPE("000F", "0F00");  // Server

    private final String opcode;
    private final String rawOpcode;

    public static ProxyPacket getByRawOpcode(String rawOpcode) {
        for (ProxyPacket packet : values()) {
            if (packet.getRawOpcode().equalsIgnoreCase(rawOpcode)) {
                return packet;
            }
        }
        throw new PacketException("Unknown proxy packet raw opcode: " + rawOpcode);
    }
}
