package com.aaemu.login.service.util;

import com.aaemu.login.service.enums.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Shannon
 */
@Component
public class ByteBufUtil {
    private static final String[] BYTE2HEX = new String[256];

    public ByteBufUtil() {
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

    public String readStringAsHex(ByteBuf byteBuf) {
        short length = readShort(byteBuf);
        StringBuilder stringBuilder = new StringBuilder();
        for (short i = 0; i < length; i++) {
            StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public String readStringAsHex(ByteBuf byteBuf, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (short i = 0; i < length; i++) {
            StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public byte readByte(ByteBuf byteBuf) {
        return byteBuf.readByte();
    }

    public short readShort(ByteBuf byteBuf) {
        return byteBuf.readShortLE();
    }

    public int readInt(ByteBuf byteBuf) {
        return byteBuf.readIntLE();
    }

    public long readLong(ByteBuf byteBuf) {
        return byteBuf.readLongLE();
    }

    public String readString(ByteBuf byteBuf) {
        return byteBuf.readCharSequence(readShort(byteBuf), StandardCharsets.US_ASCII).toString();
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

    public void writeByte(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeShort(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeInt(int value, ByteBuf byteBuf) {
        byteBuf.writeIntLE(value);
    }

    public void writeString(String value, ByteBuf byteBuf) {
        if (!StringUtils.hasText(value)) {
            writeShort(0, byteBuf);
            return;
        }
        writeShort(value.length(), byteBuf);
        byteBuf.writeCharSequence(value, StandardCharsets.US_ASCII);
    }

    public void writeBoolean(boolean value, ByteBuf byteBuf) {
        byteBuf.writeBoolean(value);
    }

    public void write(byte[] array, ByteBuf byteBuf) {
        byteBuf.writeBytes(array);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        for (int i = 0; i < packet.getRawOpcode().length(); i = i + 2) {
            byteBuf.writeByte(Integer.parseInt(packet.getRawOpcode().substring(i, i + 2), 16));
        }
    }
}
