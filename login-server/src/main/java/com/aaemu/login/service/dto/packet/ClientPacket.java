package com.aaemu.login.service.dto.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CARequestAuth("1"),
    CARequestAuthTencent("2"),
    CAChallengeResponse("3"),
    CAChallengeResponse2("4"),
    CAOtpNumber("5"),
    CAPcCertNumber("7"),
    CAListWorld("8"),
    CAEnterWorld("9"),
    CACancelEnterWorld("A"),
    CARequestReconnect("B");

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
