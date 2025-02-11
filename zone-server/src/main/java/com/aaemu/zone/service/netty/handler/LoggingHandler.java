package com.aaemu.zone.service.netty.handler;

import com.aaemu.zone.service.enums.ClientPacket;
import com.aaemu.zone.service.enums.ServerPacket;
import com.aaemu.zone.service.util.ByteBufUtils;
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
    private final ByteBufUtils byteBufUtils;
    private final boolean useIgnoreList;
    private List<ClientPacket> logIgnoreClientPackets;
    private List<ServerPacket> logIgnoreServerPackets;

    public LoggingHandler(LogLevel logLevel, ByteBufUtils byteBufUtils, boolean useIgnoreList) {
        super(logLevel);
        this.byteBufUtils = byteBufUtils;
        this.useIgnoreList = useIgnoreList;
        if (this.useIgnoreList) {
            this.logIgnoreClientPackets = new ArrayList<>();
            this.logIgnoreServerPackets = new ArrayList<>();
            initLogIgnoreClientPackets();
            initLogIgnoreServerPackets();
        }
    }

    private void initLogIgnoreClientPackets() {
//        logIgnoreClientPackets.add(ClientPacket.X2_ENTER_WORLD);
//        logIgnoreClientPackets.add(ClientPacket.CS_LIST_CHARACTER);
//        logIgnoreClientPackets.add(ClientPacket.CS_BROADCAST_VISUAL_OPTION);
//        logIgnoreClientPackets.add(ClientPacket.CS_REFRESH_IN_CHARACTER_LIST);
//        logIgnoreClientPackets.add(ClientPacket.CS_LEAVE_WORLD);
    }

    private void initLogIgnoreServerPackets() {
//        logIgnoreServerPackets.add(ServerPacket.X2_ENTER_WORLD_RESPONSE);
//        logIgnoreServerPackets.add(ServerPacket.SC_INITIAL_CONFIG);
//        logIgnoreServerPackets.add(ServerPacket.SC_ACCOUNT_INFO);
//        logIgnoreServerPackets.add(ServerPacket.SC_CHAT_SPAM_DELAY);
//        logIgnoreServerPackets.add(ServerPacket.SC_CHARACTER_LIST);
//        logIgnoreServerPackets.add(ServerPacket.SC_REFRESH_IN_CHARACTER_LIST);
//        logIgnoreServerPackets.add(ServerPacket.SC_ACCOUNT_WARNED);
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
            ((ByteBuf) msg).readerIndex(2);
            ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtils.readOpcode(((ByteBuf) msg)));
            ((ByteBuf) msg).readerIndex(0);
            if (!logIgnoreClientPackets.contains(clientPacket)) {
                logger.log(internalLevel, format(ctx, "READ", msg));
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
            ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtils.readOpcode(((ByteBuf) msg)));
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
