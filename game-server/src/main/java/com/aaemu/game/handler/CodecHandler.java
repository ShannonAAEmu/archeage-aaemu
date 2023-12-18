package com.aaemu.game.handler;


import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.enums.ClientPacket;
import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.exception.PacketException;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CodecHandler extends ByteToMessageCodec<ByteBuf> {
    private final ByteBufUtil byteBufUtil;
    private final LogHandler logHandler;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        msg.readerIndex(1);
        byte level = msg.readByte();
        if (level == PacketLevel.FIRST.getLevel()) {
            ServerPacket packet = ServerPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            System.out.println();
            System.out.printf("Send game packet: %s [opcode: %s, length: %d]%s", packet.name(), packet.getOpcode(), length, System.lineSeparator());
        } else if (level == PacketLevel.SECOND.getLevel()) {
            ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            if (!packet.equals(ProxyPacket.PONG)) {
                System.out.println();
                System.out.printf("Send proxy packet: %s [opcode: %s, length: %d]%s", packet.name(), packet.getOpcode(), length, System.lineSeparator());
            }
        }
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtil.readW(msg);
        if (length > msg.readableBytes()) {
            throw new PacketException(String.format("Received not full game packet. Expect: %d, present: %d", length, msg.readableBytes()));
        }
        int level = byteBufUtil.readLevel(msg);
        if (level == PacketLevel.FIRST.getLevel()) {
            ClientPacket packet = ClientPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            System.out.printf("Received game packet: %s [opcode: %s, length: %d, level: %d]%s", packet.name(), packet.getOpcode(), length, level, System.lineSeparator());
            buildGamePacket(packet, out, msg);
            if (msg.readableBytes() != 0) {
                logHandler.recursiveLog(ctx, msg);
                decode(ctx, msg, out);
            }
        } else if (level == PacketLevel.SECOND.getLevel()) {
            ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            if (!packet.equals(ProxyPacket.PING)) {
                System.out.printf("Received proxy packet: %s [opcode: %s, length: %d, level: %d]%s", packet.name(), packet.getOpcode(), length, level, System.lineSeparator());
            }
            buildProxyPacket(packet, out, msg);
            if (msg.readableBytes() != 0) {
                logHandler.recursiveLog(ctx, msg);
                decode(ctx, msg, out);
            }
        } else {
            throw new PacketException(String.format("Unknown packet level: %d", level));
        }
    }

    private void buildGamePacket(ClientPacket packet, List<Object> out, ByteBuf msg) {
        switch (packet) {
            case X2_ENTER_WORLD -> out.add(new X2EnterWorld(byteBufUtil, msg));
            case CS_LIST_CHARACTER -> out.add(new CSListCharacter());
            case CS_REFRESH_IN_CHARACTER_LIST -> out.add(new CSRefreshInCharacterList());
            case CS_BROADCAST_VISUAL_OPTION -> out.add(new CSBroadcastVisualOption(byteBufUtil, msg));
            case CS_CREATE_CHARACTER -> out.add(new CSCreateCharacter(byteBufUtil, msg));
        }
    }

    private void buildProxyPacket(ProxyPacket packet, List<Object> out, ByteBuf msg) {
        switch (packet) {
            case PING -> out.add(new Ping(byteBufUtil, msg));
            case FINISH_STATE -> out.add(new FinishState(byteBufUtil, msg));
        }
    }
}
