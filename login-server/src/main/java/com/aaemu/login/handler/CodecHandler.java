package com.aaemu.login.handler;

import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.dto.packet.ServerPacket;
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
import com.aaemu.login.util.ByteBufUtil;
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
        ServerPacket packet = ServerPacket.getByOpcode(byteBufUtil.readOpcode(msg));
        log.info("Send login packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), length);
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtil.readW(msg);
        ClientPacket clientPacket = ClientPacket.getByOpcode(byteBufUtil.readOpcode(msg));
        log.info("Received packet: {} [opcode: {}, length: {}]", clientPacket.name(), clientPacket.getOpcode(), length);
        switch (clientPacket) {
            case CARequestAuth -> out.add(new CARequestAuth(byteBufUtil, msg));
            case CARequestAuthTencent -> out.add(new CARequestAuthTencent(byteBufUtil, msg));
            case CAChallengeResponse -> out.add(new CAChallengeResponse(byteBufUtil, msg));
            case CAChallengeResponse2 -> out.add(new CAChallengeResponse2(byteBufUtil, msg));
            case CAOtpNumber -> out.add(new CAOtpNumber(byteBufUtil, msg));
            case CAPcCertNumber -> out.add(new CAPcCertNumber(byteBufUtil, msg));
            case CAListWorld -> out.add(new CAListWorld(byteBufUtil, msg));
            case CAEnterWorld -> out.add(new CAEnterWorld(byteBufUtil, msg));
            case CACancelEnterWorld -> out.add(new CACancelEnterWorld(byteBufUtil, msg));
            case CARequestReconnect -> out.add(new CARequestReconnect(byteBufUtil, msg));
        }
        if (msg.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, size: %d]", clientPacket.name(), clientPacket.getOpcode(), msg.readableBytes()));
        }
    }
}
