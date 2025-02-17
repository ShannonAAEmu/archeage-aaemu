package com.aaemu.login.service.enums.packet;

import com.aaemu.login.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClientPacket {
    CA_REQUEST_AUTH("0001", "0100"),
    CA_REQUEST_AUTH_TENCENT("0002", "0200"),
    CA_CHALLENGE_RESPONSE("0003", "0300"),
    CA_CHALLENGE_RESPONSE_2("0004", "0400"),
    CA_OTP_NUMBER("0005", "0500"),
    CA_TEST_ARS("0006", "0600"),
    CA_PC_CERT_NUMBER("0007", "0700"),
    CA_LIST_WORLD("0008", "0800"),
    CA_ENTER_WORLD("0009", "0900"),
    CA_CANCEL_ENTER_WORLD("000A", "0A00"),
    CA_REQUEST_RECONNECT("000B", "0B00");

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
