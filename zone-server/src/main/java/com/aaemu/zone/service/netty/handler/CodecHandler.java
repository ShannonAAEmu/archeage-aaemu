package com.aaemu.zone.service.netty.handler;

import com.aaemu.zone.service.dto.packet.client.ZWJoinPacket;
import com.aaemu.zone.service.enums.ClientPacket;
import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.util.ByteBufUtil;
import com.aaemu.zone.service.util.ChannelUtil;
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
    private static final String RECEIVED_PACKET = "Received packet: {} [opcode: {}, raw opcode: {}, length: {}, address: {}]";
    private static final String SEND_PACKET = "Send packet: {} [opcode: {}, raw opcode: {}, length: {}, address: {}]";
    private static final String LINE = StringUtils.repeat('-', 100);
    private static final String RECEIVED = "Received: {}{}";
    private static final String SEND = "Send: {}";
    private static final String BRACKETS = "{}{}";
    private final ByteBufUtil byteBufUtil;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        log.info(SEND_PACKET, serverPacket.name(), serverPacket.getOpcode(), serverPacket.getRawOpcode(), length, ChannelUtil.getChannel(ctx).remoteAddress().toString());
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
        log.info(SEND, byteBufUtil.toHex(out));
        log.info(BRACKETS, LINE, System.lineSeparator());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtil.readShort(msg);
        ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        log.info(LINE);
        log.info(RECEIVED_PACKET, clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), length, ChannelUtil.getChannel(ctx).remoteAddress().toString());
        int pos = msg.readerIndex();
        msg.readerIndex(0);
        log.info(RECEIVED, byteBufUtil.toHex(msg), System.lineSeparator());
        msg.readerIndex(pos);
        switch (clientPacket) {
            case ZW_Join -> out.add(new ZWJoinPacket(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, raw opcode: %s, size: %d, address: %s]", clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), msg.readableBytes(), ChannelUtil.getChannel(ctx)));
        }
    }
}
