package com.aaemu.stream.service.util;

import com.aaemu.stream.service.enums.ServerPacket;
import io.netty.buffer.ByteBuf;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

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

    public short readShort(ByteBuf byteBuf) {
        return byteBuf.readShortLE();
    }

    public int readInt(ByteBuf byteBuf) {
        return byteBuf.readIntLE();
    }

    public String readOpcode(ByteBuf byteBuf) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            io.netty.util.internal.StringUtil.byteToHexStringPadded(stringBuilder, byteBuf.readUnsignedByte());
        }
        return stringBuilder.toString();
    }

    public void writeByte(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        for (int i = 0; i < packet.getRawOpcode().length(); i = i + 2) {
            byteBuf.writeByte(Integer.parseInt(packet.getRawOpcode().substring(i, i + 2), 16));
        }
    }

    public void printBuf(ByteBuf byteBuf) {
        int index = byteBuf.readerIndex();
        StringBuilder stringBuilder = new StringBuilder(System.lineSeparator());
        io.netty.buffer.ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf);
        log.info(stringBuilder);
        byteBuf.readerIndex(index);
    }
}
