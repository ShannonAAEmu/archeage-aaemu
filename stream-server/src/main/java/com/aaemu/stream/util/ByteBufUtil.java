package com.aaemu.stream.util;

import com.aaemu.stream.service.enums.ServerPacket;
import com.aaemu.stream.service.exception.PacketException;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class ByteBufUtil {
    public final Charset charset;

    private String appendZero(String string) {
        if (string.length() == 1) {
            return "0".concat(string);
        }
        return string;
    }

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    private String getHexOpcode(String opcode) {
        String opcodeHex;
        if (opcode.length() == 1) {
            opcodeHex = String.format("0%s00", opcode);
        } else if (opcode.length() == 2) {
            opcodeHex = String.format("%s00", opcode);
        } else if (opcode.length() == 3) {
            String partOne = opcode.substring(0, 1);
            String partTwo = opcode.substring(1);
            opcodeHex = String.format("%s0%s", partTwo, partOne);
        } else if (opcode.length() == 4) {
            String partOne = opcode.substring(0, 2);
            String partTwo = opcode.substring(2);
            opcodeHex = String.format("%s%s", partTwo, partOne);
        } else {
            throw new PacketException("Empty opcode");
        }
        return hexToAscii(opcodeHex);
    }

    public int readW(ByteBuf byteBuf) {
        return byteBuf.readUnsignedShortLE();
    }

    public long readD(ByteBuf byteBuf) {
        return byteBuf.readUnsignedIntLE();
    }

    public String readOpcode(ByteBuf byteBuf) {
        ByteBuf opcodeBytes = byteBuf.readBytes(2);
        String firstOpcodeByte = StringUtil.byteToHexString(opcodeBytes.readByte()).toUpperCase();
        String secondOpcodeByte = StringUtil.byteToHexString(opcodeBytes.readByte()).toUpperCase();
        if (secondOpcodeByte.equals("0")) {
            if (firstOpcodeByte.endsWith("0")) {
                return String.valueOf(firstOpcodeByte.charAt(0));
            }
            return firstOpcodeByte;
        }
        if (firstOpcodeByte.endsWith("0")) {
            return secondOpcodeByte + appendZero(String.valueOf(firstOpcodeByte.charAt(0)));
        }
        return secondOpcodeByte + appendZero(firstOpcodeByte);
    }

    public void writeB(byte value, ByteBuf byteBuf) {
        byteBuf.writeByte(value);
    }

    public void writeOpcode(ServerPacket packet, ByteBuf byteBuf) {
        byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), charset);
    }
}
