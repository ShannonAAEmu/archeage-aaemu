package com.aaemu.login.service.util;

import com.aaemu.login.service.enums.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class ByteBufUtils {
    private static final String[] BYTE2HEX = new String[256];

    public ByteBufUtils() {
        for (int i = 0; i < BYTE2HEX.length; ++i) {
            BYTE2HEX[i] = StringUtil.byteToHexStringPadded(i);
        }
    }

    public String toHex(ByteBuf byteBuf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteBuf.readableBytes(); ++i) {
            sb.append(BYTE2HEX[byteBuf.getUnsignedByte(i)]);
        }
        return sb.toString().toUpperCase();
    }

    private String appendZero(String string) {
        if (string.length() == 1) {
            return "0".concat(string);
        }
        return string;
    }

    private String getHexOpcode(String opcode) {
        return StringUtils.toString(opcode.substring(2, 4) + opcode.substring(0, 2));
    }

    public String readStringAsHex(ByteBuf byteBuf) {
        int length = readW(byteBuf);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public String readHex(ByteBuf byteBuf, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public int readB(ByteBuf byteBuf) {
        return byteBuf.readUnsignedByte();
    }

    public int readW(ByteBuf byteBuf) {
        return byteBuf.readUnsignedShortLE();
    }

    public long readD(ByteBuf byteBuf) {
        return byteBuf.readUnsignedIntLE();
    }

    public long readQ(ByteBuf byteBuf) {
        return byteBuf.readLongLE();
    }

    public String readS(ByteBuf byteBuf) {
        return byteBuf.readCharSequence(readW(byteBuf), StandardCharsets.US_ASCII).toString();
    }

    public String readS(int length, ByteBuf byteBuf) {
        return byteBuf.readCharSequence(length, StandardCharsets.US_ASCII).toString();
    }

    public boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    public String readOpcode(ByteBuf byteBuf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public void writeB(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeW(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeD(int value, ByteBuf byteBuf) {
        byteBuf.writeIntLE(value);
    }

    public void writeQ(long value, ByteBuf byteBuf) {
        byteBuf.writeLongLE(value);
    }

    public void writeS(String value, ByteBuf byteBuf) {
        if (value == null || value.isBlank()) {
            writeW(0, byteBuf);
            return;
        }
        writeW(value.length(), byteBuf);
        byteBuf.writeCharSequence(value, StandardCharsets.US_ASCII);
    }

    public void writeBoolean(boolean value, ByteBuf byteBuf) {
        byteBuf.writeBoolean(value);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        byteBuf.writeCharSequence(StringUtils.toString(packet.getRawOpcode()), StandardCharsets.US_ASCII);
    }
}
