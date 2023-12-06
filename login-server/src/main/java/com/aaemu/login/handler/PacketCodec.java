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
public class PacketCodec extends ByteToMessageCodec<ByteBuf> {

    private final ByteBufUtil byteBufUtil;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
        ServerPacket packet = ServerPacket.getByOpcode(Integer.toHexString(msg.readUnsignedShortLE()));
        log.info("Send packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), msg.readableBytes());
        msg.readerIndex(0);
        out.writeShortLE(msg.readableBytes());
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int length = byteBufUtil.readW(in);
        ClientPacket clientPacket = ClientPacket.getByOpcode(Integer.toHexString(byteBufUtil.readW(in)));
        log.info("Received packet: {} [opcode: {}, length: {}]", clientPacket.name(), clientPacket.getOpcode(), length);
        switch (clientPacket) {
            case CARequestAuth -> out.add(new CARequestAuth(byteBufUtil, in));
            case CARequestAuthTencent -> out.add(new CARequestAuthTencent(byteBufUtil, in));
            case CAChallengeResponse -> out.add(new CAChallengeResponse(byteBufUtil, in));
            case CAChallengeResponse2 -> out.add(new CAChallengeResponse2(byteBufUtil, in));
            case CAOtpNumber -> out.add(new CAOtpNumber(byteBufUtil, in));
            case CAPcCertNumber -> out.add(new CAPcCertNumber(byteBufUtil, in));
            case CAListWorld -> out.add(new CAListWorld(byteBufUtil, in));
            case CAEnterWorld -> out.add(new CAEnterWorld(byteBufUtil, in));
            case CACancelEnterWorld -> out.add(new CACancelEnterWorld(byteBufUtil, in));
            case CARequestReconnect -> out.add(new CARequestReconnect(byteBufUtil, in));
        }
        if (in.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, size: %d]", clientPacket.name(), clientPacket.getOpcode(), in.readableBytes()));
        }
    }
}
