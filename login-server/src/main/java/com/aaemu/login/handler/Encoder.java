package com.aaemu.login.handler;

import com.aaemu.login.service.entity.packet.ServerPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class Encoder extends MessageToMessageEncoder<ByteBuf> {
    private static final int PACKET_LENGTH_SIZE = 2;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        ServerPacket packet = ServerPacket.getByOpcode(Integer.toHexString(msg.readUnsignedShortLE()));
        log.info("Send packet: {} [opcode: {}, length: {}]", packet.name(), packet.getOpcode(), msg.readableBytes());
        msg.readerIndex(0);
        out.add(Unpooled.buffer(msg.readableBytes() + PACKET_LENGTH_SIZE)
                .writeShortLE(msg.readableBytes())
                .writeBytes(msg)
        );
    }
}
