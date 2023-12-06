package com.aaemu.login.service.entity.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CARequestAuth("1"),
    CAChallengeResponse("3"),
    CAChallengeResponse2("4"),
    CAOtpNumber("5"),
    CAListWorld("8");

    private final String opcode;

    public static ClientPacket getByOpcode(String opcode) {
        for (ClientPacket packet : values()) {
            if (packet.getOpcode().equalsIgnoreCase(opcode)) {
                return packet;
            }
        }
        throw new RuntimeException(String.format("Unknown client packet opcode: %s", opcode));
    }
}
