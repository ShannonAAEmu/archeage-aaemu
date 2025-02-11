package com.aaemu.login.service.enums;

import com.aaemu.login.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CARequestAuth("0001", "0100"),
    CARequestAuthTencent("0002", "0200"),
    CAChallengeResponse("0003", "0300"),
    CAChallengeResponse2("0004", "0400"),
    CAOtpNumber("0005", "0500"),
    CAPcCertNumber("0007", "0700"),
    CAListWorld("0008", "0800"),
    CAEnterWorld("0009", "0900"),
    CACancelEnterWorld("000A", "0A00"),
    CARequestReconnect("000B", "0B00");

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
