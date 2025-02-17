package com.aaemu.login.service.enums.packet;

import com.aaemu.login.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServerPacket {
    AC_JOIN_RESPONSE("0000", "0000"),
    AC_CHALLENGE("0002", "0200"),
    AC_AUTH_RESPONSE("0003", "0300"),
    AC_CHALLENGE_2("0004", "0400"),
    AC_ENTER_OTP("0005", "0500"),
    AC_SHOW_ARS("0006", "0600"),
    AC_ENTER_PC_CERT("0007", "0700"),
    AC_WORLD_LIST("0008", "0800"),
    AC_WORLD_QUEUE("0009", "0900"),
    AC_WORLD_COOKIE("000A", "0A00"),
    AC_ENTER_WORLD_DENIED("000B", "0B00"),
    AC_LOGIN_DENIED("000C", "0C00"),
    AC_ACCOUNT_WARNED("000D", "0D00");

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
