package com.aaemu.login.service.enums;

import com.aaemu.login.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    ACJoinResponse("0000", "0000"),
    ACChallenge("0002", "0200"),
    ACAuthResponse("0003", "0300"),
    ACChallenge2("0004", "0400"),
    ACEnterOtp("0005", "0500"),
    ACShowArs("0006", "0600"),
    ACEnterPcCert("0007", "0700"),
    ACWorldList("0008", "0800"),
    ACWorldQueue("0009", "0900"),
    ACWorldCookie("000A", "0A00"),
    ACEnterWorldDenied("000B", "0B00"),
    ACLoginDenied("000C", "0C00"),
    ACAccountWarned("000D", "0D00");

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
