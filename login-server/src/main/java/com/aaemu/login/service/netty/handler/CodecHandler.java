package com.aaemu.login.service.netty.handler;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestAuthTencent;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.client.CATestArs;
import com.aaemu.login.service.enums.packet.ClientPacket;
import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
import com.aaemu.login.service.util.ChannelUtil;
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
    private final boolean isEditorMode;

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
        short length = byteBufUtil.readShort(msg);
        ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        log.info(LINE);
        log.info(RECEIVED_PACKET, clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), length, ChannelUtil.getChannel(ctx).remoteAddress().toString());
        int pos = msg.readerIndex();
        msg.readerIndex(0);
        log.info(RECEIVED, byteBufUtil.toHex(msg), System.lineSeparator());
        msg.readerIndex(pos);
        switch (clientPacket) {
            case CA_REQUEST_AUTH -> out.add(new CARequestAuth(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_REQUEST_AUTH_TENCENT -> out.add(new CARequestAuthTencent(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_CHALLENGE_RESPONSE -> out.add(new CAChallengeResponse(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_OTP_NUMBER -> out.add(new CAOtpNumber(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_TEST_ARS -> out.add(new CATestArs(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_PC_CERT_NUMBER -> out.add(new CAPcCertNumber(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_LIST_WORLD -> out.add(new CAListWorld(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_ENTER_WORLD -> out.add(new CAEnterWorld(ChannelUtil.getChannel(ctx), byteBufUtil, msg, isEditorMode));
            case CA_CANCEL_ENTER_WORLD -> out.add(new CACancelEnterWorld(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
            case CA_REQUEST_RECONNECT -> out.add(new CARequestReconnect(ChannelUtil.getChannel(ctx), byteBufUtil, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, raw opcode: %s, size: %d, address: %s]", clientPacket.name(), clientPacket.getOpcode(), clientPacket.getRawOpcode(), msg.readableBytes(), ChannelUtil.getChannel(ctx)));
        }
    }
}
