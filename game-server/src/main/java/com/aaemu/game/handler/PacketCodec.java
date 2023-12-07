package com.aaemu.game.handler;


import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.dto.packet.ServerPacket;
import com.aaemu.game.util.ByteBufUtil;
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
//        ServerPacket packet = ServerPacket.getByOpcode(Integer.toHexString(msg.readUnsignedShortLE()));
//        log.info("Send packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), msg.readableBytes());
//        msg.readerIndex(0);
//        out.writeShortLE(msg.readableBytes());
//        out.writeBytes(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
//        int length = byteBufUtil.readW(in);
//        ClientPacket clientPacket = ClientPacket.getByOpcode(Integer.toHexString(byteBufUtil.readW(in)));
//        log.info("Received packet: {} [opcode: {}, length: {}]", clientPacket.name(), clientPacket.getOpcode(), length);
////        switch (clientPacket) {
////
////        }
//        if (in.readableBytes() != 0) {
//            throw new RuntimeException(String.format("Not all bytes were read from the client packet: %s [opcode: %s, size: %d]", clientPacket.name(), clientPacket.getOpcode(), in.readableBytes()));
//        }
    }
}
