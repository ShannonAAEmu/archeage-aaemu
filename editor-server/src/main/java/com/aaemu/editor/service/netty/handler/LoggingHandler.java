package com.aaemu.editor.service.netty.handler;

import com.aaemu.editor.service.enums.ClientPacket;
import com.aaemu.editor.service.enums.ServerPacket;
import com.aaemu.editor.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shannon
 */
public class LoggingHandler extends io.netty.handler.logging.LoggingHandler {
    private final ByteBufUtil byteBufUtil;
    private final boolean useIgnoreList;
    private List<ClientPacket> logIgnoreClientPackets;
    private List<ServerPacket> logIgnoreServerPackets;

    public LoggingHandler(LogLevel logLevel, ByteBufUtil byteBufUtil, boolean useIgnoreList) {
        super(logLevel);
        this.byteBufUtil = byteBufUtil;
        this.useIgnoreList = useIgnoreList;
        if (this.useIgnoreList) {
            this.logIgnoreClientPackets = new ArrayList<>();
            this.logIgnoreServerPackets = new ArrayList<>();
            initLogIgnoreClientPackets();
            initLogIgnoreServerPackets();
        }
    }

    private void initLogIgnoreClientPackets() {
        logIgnoreClientPackets.add(ClientPacket.CE_LOGIN);
        logIgnoreClientPackets.add(ClientPacket.EC_PONG);
    }

    private void initLogIgnoreServerPackets() {
        logIgnoreServerPackets.add(ServerPacket.EC_LOGIN_RESPONSE);
        logIgnoreServerPackets.add(ServerPacket.EC_PING);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.fireChannelInactive();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!useIgnoreList) {
            super.channelRead(ctx, msg);
            return;
        }
        if (logger.isEnabled(internalLevel)) {
            try {
                ((ByteBuf) msg).readerIndex(2);
                ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtil.readOpcode(((ByteBuf) msg)));
                ((ByteBuf) msg).readerIndex(0);
                if (!logIgnoreClientPackets.contains(clientPacket)) {
                    logger.log(internalLevel, format(ctx, "READ", msg));
                }
            } catch (Exception e) {
                ((ByteBuf) msg).readerIndex(0);
                byteBufUtil.printBuf((ByteBuf) msg);
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!useIgnoreList) {
            super.write(ctx, msg, promise);
            return;
        }
        if (logger.isEnabled(internalLevel)) {
            ((ByteBuf) msg).readerIndex(2);
            ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtil.readOpcode(((ByteBuf) msg)));
            ((ByteBuf) msg).readerIndex(0);
            if (!logIgnoreServerPackets.contains(serverPacket)) {
                logger.log(internalLevel, format(ctx, "WRITE", msg));
            }
        }
        ctx.write(msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.fireChannelReadComplete();
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) {
        ctx.close(promise);
    }
}
