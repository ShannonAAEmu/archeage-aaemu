package com.aaemu.login.service.netty.handler;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestAuthTencent;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.enums.ClientPacket;
import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
import com.aaemu.login.service.util.ChannelUtils;
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
    private final ByteBufUtils byteBufUtils;
    private final boolean isEditorMode;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        int length = msg.readableBytes();
        ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
        log.info(SEND_PACKET, serverPacket.name(), serverPacket.getOpcode(), serverPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString());
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
        log.info(SEND, byteBufUtils.toHex(out));
        log.info(BRACKETS, LINE, System.lineSeparator());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtils.readW(msg);
        ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtils.readOpcode(msg));
        log.info(LINE);
        log.info(RECEIVED_PACKET, clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), length, ChannelUtils.getChannel(ctx).remoteAddress().toString());
        int pos = msg.readerIndex();
        msg.readerIndex(0);
        log.info(RECEIVED, byteBufUtils.toHex(msg), System.lineSeparator());
        msg.readerIndex(pos);
        switch (clientPacket) {
            case CARequestAuth -> out.add(new CARequestAuth(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CARequestAuthTencent -> out.add(new CARequestAuthTencent(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAChallengeResponse -> out.add(new CAChallengeResponse(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAChallengeResponse2 -> out.add(new CAChallengeResponse2(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAOtpNumber -> out.add(new CAOtpNumber(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAPcCertNumber -> out.add(new CAPcCertNumber(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAListWorld -> out.add(new CAListWorld(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CAEnterWorld -> out.add(new CAEnterWorld(ChannelUtils.getChannel(ctx), byteBufUtils, msg, isEditorMode));
            case CACancelEnterWorld -> out.add(new CACancelEnterWorld(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
            case CARequestReconnect -> out.add(new CARequestReconnect(ChannelUtils.getChannel(ctx), byteBufUtils, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, raw opcode: %s, size: %d, address: %s]", clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), msg.readableBytes(), ChannelUtils.getChannel(ctx)));
        }
    }
}
