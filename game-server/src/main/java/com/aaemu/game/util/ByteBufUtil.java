package com.aaemu.game.util;

import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.exception.PacketException;
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

    public float readFloat(ByteBuf byteBuf) {
        return byteBuf.readFloatLE();
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

    public void writeW(int value, ByteBuf byteBuf) {
        byteBuf.writeShortLE(value);
    }

    public void writeD(int value, ByteBuf byteBuf) {
        byteBuf.writeIntLE(value);
    }

    public void writeQ(long value, ByteBuf byteBuf) {
        byteBuf.writeLongLE(value);
    }

    public void writeF(float value, ByteBuf byteBuf) {
        byteBuf.writeFloatLE(value);
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
            case ServerPacket packet -> byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), charset);
            case ProxyPacket packet -> byteBuf.writeCharSequence(getHexOpcode(packet.getOpcode()), charset);
            default -> throw new PacketException(String.format("Unknown server packet: %s", rawPacket));
        }
    }
}
