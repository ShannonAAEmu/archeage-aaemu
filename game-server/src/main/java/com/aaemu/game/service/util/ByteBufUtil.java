package com.aaemu.game.service.util;

import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ProxyPacket;
import com.aaemu.game.service.enums.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author Shannon
 */
@Component
@Log4j2
public class ByteBufUtil {
    private static final String[] BYTE2HEX = new String[256];

    public ByteBufUtil() {
        for (int i = 0; i < BYTE2HEX.length; ++i) {
            BYTE2HEX[i] = io.netty.util.internal.StringUtil.byteToHexStringPadded(i);
        }
    }

    public String toHex(ByteBuf byteBuf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteBuf.readableBytes(); ++i) {
            sb.append(BYTE2HEX[byteBuf.getUnsignedByte(i)]);
        }
        return sb.toString().toUpperCase();
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

    public float readFloat(ByteBuf byteBuf) {
        return byteBuf.readFloatLE();
    }

    public boolean readBoolean(ByteBuf byteBuf) {
        return byteBuf.readBoolean();
    }

    public String readPacketLevel(ByteBuf byteBuf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            io.netty.util.internal.StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public String readOpcode(ByteBuf byteBuf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            io.netty.util.internal.StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public void writeByte(int value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeShort(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeInt(int value, ByteBuf byteBuf) {
        byteBuf.writeIntLE(value);
    }

    public void writeLong(long value, ByteBuf byteBuf) {
        byteBuf.writeLongLE(value);
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

    public void writeFloat(float value, ByteBuf byteBuf) {
        byteBuf.writeFloatLE(value);
    }

    public void write(ByteBuf source, ByteBuf target) {
        target.writeBytes(source);
        ReferenceCountUtil.safeRelease(source);
    }

    public void write(byte[] array, ByteBuf byteBuf) {
        byteBuf.writeBytes(array);
    }

    public void writeLevel(PacketLevel level, ByteBuf byteBuf) {
        byteBuf.writeCharSequence(StringUtil.toString(level.getServerRawLevel()), StandardCharsets.US_ASCII);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        for (int i = 0; i < packet.getRawOpcode().length(); i = i + 2) {
            byteBuf.writeByte(Integer.parseInt(packet.getRawOpcode().substring(i, i + 2), 16));
        }
    }

    public void writeOpcode(ProxyPacket packet, ByteBuf byteBuf) {
        for (int i = 0; i < packet.getRawOpcode().length(); i = i + 2) {
            byteBuf.writeByte(Integer.parseInt(packet.getRawOpcode().substring(i, i + 2), 16));
        }
    }

    public void writeHex(String packet, ByteBuf byteBuf) {
        for (int i = 0; i < packet.length(); i = i + 2) {
            byteBuf.writeByte(Integer.parseInt(packet.substring(i, i + 2), 16));
        }
    }

    public void printBuf(ByteBuf byteBuf) {
        StringBuilder stringBuilder = new StringBuilder(System.lineSeparator());
        io.netty.buffer.ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf);
        log.info(stringBuilder);
    }
}
