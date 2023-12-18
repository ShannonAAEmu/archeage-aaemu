package com.aaemu.game.handler;

import com.aaemu.game.service.enums.PacketLevel;
import com.aaemu.game.service.enums.ProxyPacket;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;

@ChannelHandler.Sharable
@Slf4j
public class LogHandler extends LoggingHandler {
    private final ByteBufUtil byteBufUtil;

    public LogHandler(ByteBufUtil byteBufUtil, LogLevel level) {
        super(level);
        this.byteBufUtil = byteBufUtil;
    }

    private boolean isPingPongPacket(ProxyPacket packet) {
        return packet.getOpcode().equals(ProxyPacket.PING.getOpcode()) || packet.getOpcode().equals(ProxyPacket.PONG.getOpcode());
    }

    public void recursiveLog(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof ByteBuf byteBuf) {
                byteBuf.readerIndex(byteBuf.readerIndex() + 3);
                byte level = byteBuf.readByte();
                if (level == PacketLevel.SECOND.getLevel()) {
                    ProxyPacket packet = ProxyPacket.getByOpcode(byteBufUtil.readOpcode(byteBuf));
                    byteBuf.readerIndex(byteBuf.readerIndex() - 6);
                    if (isPingPongPacket(packet)) {
                        return;
                    }
                } else {
                    byteBuf.readerIndex(byteBuf.readerIndex() - 4);
                }
                StringBuilder stringBuilder = new StringBuilder();
                System.out.println();
                System.out.println(">===========================================================================<");
                appendPrettyHexDump(stringBuilder, byteBuf);
                System.out.println(stringBuilder);
            }
        } catch (Exception e) {
            log.error("Disconnect client.", e);
            ctx.close();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
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
                StringBuilder stringBuilder = new StringBuilder();
                System.out.println();
                System.out.println(">===========================================================================<");
                appendPrettyHexDump(stringBuilder, byteBuf);
                System.out.println(stringBuilder);
                ctx.fireChannelRead(byteBuf);
                return;
            }
            super.channelRead(ctx, msg);
        } catch (Exception e) {
            log.error("Disconnect client.", e);
            ctx.close();
        }
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
            StringBuilder stringBuilder = new StringBuilder();
            appendPrettyHexDump(stringBuilder, byteBuf);
            System.out.println(stringBuilder);
            ctx.write(byteBuf, promise);
            return;
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.fireChannelInactive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        ctx.fireChannelUnregistered();
    }
}
