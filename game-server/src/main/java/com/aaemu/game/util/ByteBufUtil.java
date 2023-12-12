package com.aaemu.game.util;

import com.aaemu.game.service.dto.packet.ProxyPacket;
import com.aaemu.game.service.dto.packet.ServerPacket;
import com.aaemu.game.service.exception.PacketException;
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

    public String readS(ByteBuf byteBuf) {
        return byteBuf.readCharSequence(readW(byteBuf), charset).toString();
    }

    public String readS(int length, ByteBuf byteBuf) {
        return byteBuf.readCharSequence(length, charset).toString();
    }

    public boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    public int readLevel(ByteBuf byteBuf) {
        readB(byteBuf);     // Unknown byte (changed every time)
        return readB(byteBuf);
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
        if (isLittleEndianByteOrder) {
            byteBuf.writeShortLE(value);
        } else {
            byteBuf.writeShort(value);
        }
    }

    public void writeD(int value, ByteBuf byteBuf) {
        if (isLittleEndianByteOrder) {
            byteBuf.writeIntLE(value);
        } else {
            byteBuf.writeInt(value);
        }
    }

    public void writeQ(long value, ByteBuf byteBuf) {
        if (isLittleEndianByteOrder) {
            byteBuf.writeLongLE(value);
        } else {
            byteBuf.writeLong(value);
        }
    }

    public void writeF(float value, ByteBuf byteBuf) {
        if (isLittleEndianByteOrder) {
            byteBuf.writeFloatLE(value);
        } else {
            byteBuf.writeFloat(value);
        }
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

    public void writeLevel(int level, ByteBuf byteBuf) {
        writeW(Integer.parseInt(String.format("0%dDD", level), 16), byteBuf);
    }

    public void writeOpcode(Object rawPacket, ByteBuf byteBuf) {
        switch (rawPacket) {
            case ServerPacket packet ->
                    byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), StandardCharsets.US_ASCII);
            case ProxyPacket packet ->
                    byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), StandardCharsets.US_ASCII);
            default -> throw new PacketException(String.format("Unknown server packet: %s", rawPacket));
        }
    }

}
