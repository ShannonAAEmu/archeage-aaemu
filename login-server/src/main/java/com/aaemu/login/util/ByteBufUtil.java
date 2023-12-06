package com.aaemu.login.util;

import io.netty.buffer.ByteBuf;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class ByteBufUtil {
    public final boolean isLittleEndianByteOrder;
    public final Charset charset;

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

    public void writeB(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeW(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeOpcode(String value, ByteBuf byteBuf) {
        if (value.chars().allMatch(Character::isDigit)) {
            byteBuf.writeShortLE(Integer.parseInt(value));
        } else {
            byteBuf.writeShortLE(Integer.parseInt(value, 16));
        }
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
