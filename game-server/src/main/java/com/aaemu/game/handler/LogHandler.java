package com.aaemu.game.handler;

import com.aaemu.game.service.dto.packet.PacketLevel;
import com.aaemu.game.service.dto.packet.ProxyPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@ChannelHandler.Sharable
public class LogHandler extends LoggingHandler {
    private final ByteBufUtil byteBufUtil;

    public LogHandler(ByteBufUtil byteBufUtil, LogLevel level) {
        super(level);
        this.byteBufUtil = byteBufUtil;
    }

    private boolean isPingPongPacket(ProxyPacket packet) {
        return packet.getOpcode().equals(ProxyPacket.PING.getOpcode()) || packet.getOpcode().equals(ProxyPacket.PONG.getOpcode());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            byteBuf.readerIndex(3);
            byte level = byteBuf.readByte();
            if (level == PacketLevel.SECOND.getLevel()) {
                ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(byteBuf));
                byteBuf.readerIndex(0);
                if (isPingPongPacket(packet)) {
                    ctx.fireChannelRead(byteBuf);
                    return;
                }
            }
            byteBuf.readerIndex(0);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            byteBuf.readerIndex(3);
            byte level = byteBuf.readByte();
            if (level == PacketLevel.SECOND.getLevel()) {
                ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(byteBuf));
                byteBuf.readerIndex(0);
                if (isPingPongPacket(packet)) {
                    ctx.write(byteBuf, promise);
                    return;
                }
            }
            byteBuf.readerIndex(0);
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
