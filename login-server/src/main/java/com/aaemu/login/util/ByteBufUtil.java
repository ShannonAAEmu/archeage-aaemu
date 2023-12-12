package com.aaemu.login.util;

import com.aaemu.login.service.dto.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class ByteBufUtil {
    private static final String APPEND_CHAR = "0";
    public final boolean isLittleEndianByteOrder;
    public final Charset charset;

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    private String getHexOpcode(String opcode) {
        String appendString = 4 - (4 - opcode.length()) == 1 ? "0" : "";
        String repeatString = APPEND_CHAR.repeat(4 - appendString.length() - opcode.length());
        String opcodeHex;
        if (isLittleEndianByteOrder) {
            opcodeHex = String.format("%s%s%s", appendString, opcode, repeatString);
        } else {
            opcodeHex = String.format("%s%s%s", repeatString, appendString, opcode);
        }
        return hexToAscii(opcodeHex);
    }

    public int readB(ByteBuf byteBuf) {
        return isLittleEndianByteOrder ? byteBuf.readUnsignedByte() : byteBuf.readByte();
    }

    public int readW(ByteBuf byteBuf) {
        return isLittleEndianByteOrder ? byteBuf.readUnsignedShortLE() : byteBuf.readUnsignedShort();
    }

    public long readD(ByteBuf byteBuf) {
        return isLittleEndianByteOrder ? byteBuf.readUnsignedIntLE() : byteBuf.readUnsignedInt();
    }

    public long readQ(ByteBuf byteBuf) {
        return isLittleEndianByteOrder ? byteBuf.readLongLE() : byteBuf.readLong();
    }

    public boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    public String readString(ByteBuf byteBuf) {
        return byteBuf.readCharSequence(readW(byteBuf), charset).toString();
    }

    public String readString(int length, ByteBuf byteBuf) {
        return byteBuf.readCharSequence(length, charset).toString();
    }

    public String readOpcode(ByteBuf byteBuf) {
        ByteBuf opcodeBytes = byteBuf.readBytes(2);
        StringBuilder hexOpcode = new StringBuilder();
        for (int i = 0; i < opcodeBytes.readableBytes(); i++) {
            hexOpcode.append(StringUtil.byteToHexString(opcodeBytes.readByte()).toUpperCase());
        }
        return hexOpcode.toString();
    }

    public void writeB(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeW(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), StandardCharsets.US_ASCII);
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
        byteBuf.writeCharSequence(value, charset);
    }

    public void writeBoolean(boolean value, ByteBuf byteBuf) {
        byteBuf.writeBoolean(value);
    }
}
