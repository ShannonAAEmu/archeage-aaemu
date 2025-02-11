package com.aaemu.game.service.netty.handler;

import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSLeaveWorld;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.enums.ClientPacket;
import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.service.enums.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtils;
import com.aaemu.game.service.util.ChannelUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Log4j2
public class CodecHandler extends ByteToMessageCodec<ByteBuf> {
    private static final String RECEIVED_PACKET = "Received packet: {} [level: {}, opcode: {}, raw opcode: {}, length: {}, address: {}]";
    private static final String SEND_PACKET = "Send packet: {} [level: {}, opcode: {}, raw opcode: {}, length: {}, address: {}]";
    private static final String LINE = StringUtils.repeat('-', 100);
    private static final String RECEIVED = "Received: {}{}";
    private static final String SEND = "Send: {}";
    private static final String BRACKETS = "{}{}";
    private final ByteBufUtils byteBufUtils;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        PacketLevel packetLevel = PacketLevel.getByLevel(byteBufUtils.readPacketLevel(msg));
        switch (packetLevel) {
            case _1 -> {
                ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
                log.info(SEND_PACKET, serverPacket.name(), packetLevel.getLevel(), serverPacket.getOpcode(), serverPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString());
            }
            case _2 -> {
//                ProxyPacket proxyPacket = ProxyPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
//                log.info(SEND, proxyPacket.name(), packetLevel.getLevel(), proxyPacket.getOpcode(), proxyPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString());
            }
        }
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
        if (PacketLevel._1.equals(packetLevel)) {
            log.info(SEND, byteBufUtils.toHex(out));
            log.info(BRACKETS, LINE, System.lineSeparator());
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtils.readW(msg);
        PacketLevel packetLevel = PacketLevel.getByLevel(byteBufUtils.readPacketLevel(msg));
        switch (packetLevel) {
            case _1 -> decodeGamePacket(ctx, msg, out, length, packetLevel);
            case _2 -> decodeProxyPacket(ctx, msg, out, length, packetLevel);
        }
    }

    private void decodeGamePacket(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out, int length, PacketLevel packetLevel) {
        ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
        log.info(LINE);
        log.info(RECEIVED_PACKET, clientPacket.name(), packetLevel.getLevel(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString());
        int pos = msg.readerIndex();
        msg.readerIndex(0);
        log.info(RECEIVED, byteBufUtils.toHex(msg), System.lineSeparator());
        msg.readerIndex(pos);
        switch (clientPacket) {
            case X2_ENTER_WORLD -> out.add(new X2EnterWorld(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CS_LIST_CHARACTER -> out.add(new CSListCharacter(ChannelUtils.getChannel(ctx)));
            case CS_BROADCAST_VISUAL_OPTION -> out.add(new CSBroadcastVisualOption(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CS_REFRESH_IN_CHARACTER_LIST -> out.add(new CSRefreshInCharacterList(ChannelUtils.getChannel(ctx)));
            case CS_LEAVE_WORLD -> out.add(new CSLeaveWorld(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CS_CREATE_CHARACTER -> out.add(new CSCreateCharacter(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, size: %d, address: %s]", clientPacket.name(), clientPacket.getOpcode(), msg.readableBytes(), ChannelUtils.getChannel(ctx)));
        }
    }

    private void decodeProxyPacket(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out, int length, PacketLevel packetLevel) {
        ProxyPacket proxyPacket = ProxyPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
//        log.info(RECEIVED, proxyPacket.name(), packetLevel.getLevel(), proxyPacket.getOpcode(), proxyPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString(), System.lineSeparator());
        switch (proxyPacket) {
            case PING -> out.add(new Ping(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case FINISH_STATE -> out.add(new FinishState(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the proxy packet: %s [opcode: %s, size: %d, address: %s]", proxyPacket.name(), proxyPacket.getOpcode(), msg.readableBytes(), ChannelUtils.getChannel(ctx)));
        }
    }
}
