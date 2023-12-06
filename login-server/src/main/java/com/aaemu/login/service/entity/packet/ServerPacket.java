package com.aaemu.login.service.entity.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    ACJoinResponse("0"),
    ACChallenge("2"),
    ACAuthResponse("3"),
    ACChallenge2("4"),
    ACEnterOtp("5"),
    ACShowArs("6"),
    ACWorldList("8"),
    ACLoginDenied("C");

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
