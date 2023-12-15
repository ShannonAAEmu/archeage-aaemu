package com.aaemu.game.service.enums;

import com.aaemu.game.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    X2_ENTER_WORLD("0"),
    CS_BROADCAST_VISUAL_OPTION("101"),
    CS_LIST_CHARACTER("19"),
    CS_REFRESH_IN_CHARACTER_LIST("1A"),
    CS_CREATE_CHARACTER("1B");

    private final String opcode;

    public static ClientPacket getByOpcode(String opcode) {
        for (ClientPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new PacketException(String.format("Unknown client packet opcode: %s", opcode));
    }
}
