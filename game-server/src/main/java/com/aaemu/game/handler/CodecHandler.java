package com.aaemu.game.handler;


import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.dto.packet.PacketLevel;
import com.aaemu.game.service.dto.packet.ProxyPacket;
import com.aaemu.game.service.dto.packet.ServerPacket;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.Ping;
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

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        msg.readerIndex(1);
        byte level = msg.readByte();
        if (level == PacketLevel.FIRST.getLevel()) {
            ServerPacket packet = ServerPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            log.info("Send game packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), length);
        } else if (level == PacketLevel.SECOND.getLevel()) {
            ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            if (!packet.equals(ProxyPacket.PONG)) {
                log.info("Send proxy packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), length);
            }
        }
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtil.readW(msg);
        int level = byteBufUtil.readLevel(msg);
        if (level == PacketLevel.FIRST.getLevel()) {
            ClientPacket packet = ClientPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            log.info("Received game packet: {} [opcode: {}, length: {}, level: {}]", packet.name(), packet.getOpcode(), length, level);
            buildGamePacket(packet, out, msg);
            if (msg.readableBytes() != 0) {
                log.warn("Not all bytes were read from the client game packet: {} [opcode: {}, size: {}, level: {}]", packet.name(), packet.getOpcode(), msg.readableBytes(), level);
            }
        } else if (level == PacketLevel.SECOND.getLevel()) {
            ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(msg));
            if (!packet.equals(ProxyPacket.PING)) {
                log.info("Received proxy packet: {} [opcode: {}, length: {}, level: {}]", packet.name(), packet.getOpcode(), length, level);
            }
            buildProxyPacket(packet, out, msg);
            if (msg.readableBytes() != 0) {
                log.warn("Not all bytes were read from the client proxy packet: {} [opcode: {}, size: {}, level: {}]", packet.name(), packet.getOpcode(), msg.readableBytes(), level);
            }
        } else {
            throw new PacketException(String.format("Unknown packet level: %d", level));
        }
    }

    private void buildGamePacket(ClientPacket packet, List<Object> out, ByteBuf msg) {
        switch (packet) {
            case X2_ENTER_WORLD -> out.add(new X2EnterWorld(byteBufUtil, msg));
            case CS_LIST_CHARACTER -> out.add(new CSListCharacter());
        }
    }

    private void buildProxyPacket(ProxyPacket packet, List<Object> out, ByteBuf msg) {
        switch (packet) {
            case PING -> out.add(new Ping(byteBufUtil, msg));
            case FINISH_STATE -> out.add(new FinishState(byteBufUtil, msg));
        }
    }
}
