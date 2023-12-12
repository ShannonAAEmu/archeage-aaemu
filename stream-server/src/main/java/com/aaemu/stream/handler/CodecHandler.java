package com.aaemu.stream.handler;


import com.aaemu.stream.service.dto.packet.ClientPacket;
import com.aaemu.stream.service.dto.packet.ServerPacket;
import com.aaemu.stream.service.dto.packet.client.CTJoin;
import com.aaemu.stream.util.ByteBufUtil;
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
        log.info("Send stream packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), length);
        msg.readerIndex(0);
        out.writeShortLE(length);
        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        int length = byteBufUtil.readW(msg);
        ClientPacket packet = ClientPacket.getByOpcode(byteBufUtil.readOpcode(msg));
        log.info("Received stream packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), length);
        buildStreamPacket(packet, out, msg);
        if (msg.readableBytes() != 0) {
            log.warn("Not all bytes were read from the client stream packet: {} [opcode: {}, size: {}]", packet.name(), packet.getOpcode(), msg.readableBytes());
        }
    }

    private void buildStreamPacket(ClientPacket packet, List<Object> out, ByteBuf msg) {
        switch (packet) {
            case CT_JOIN -> out.add(new CTJoin(byteBufUtil, msg));
        }
    }
}
