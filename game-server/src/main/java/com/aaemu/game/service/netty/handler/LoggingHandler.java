package com.aaemu.game.service.netty.handler;

import com.aaemu.game.service.enums.packet.ClientPacket;
import com.aaemu.game.service.enums.packet.PacketLevel;
import com.aaemu.game.service.enums.packet.ProxyPacket;
import com.aaemu.game.service.enums.packet.ServerPacket;
import com.aaemu.game.service.util.ByteBufUtil;
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
    private List<ProxyPacket> logIgnoreProxyPackets;

    public LoggingHandler(LogLevel logLevel, ByteBufUtil byteBufUtil, boolean useIgnoreList) {
        super(logLevel);
        this.byteBufUtil = byteBufUtil;
        this.useIgnoreList = useIgnoreList;
        if (this.useIgnoreList) {
            this.logIgnoreClientPackets = new ArrayList<>();
            this.logIgnoreServerPackets = new ArrayList<>();
            this.logIgnoreProxyPackets = new ArrayList<>();
            initLogIgnoreClientPackets();
            initLogIgnoreServerPackets();
            initLogIgnoreProxyPackets();
        }
    }

    private void initLogIgnoreClientPackets() {
        logIgnoreClientPackets.add(ClientPacket.X2_ENTER_WORLD);
        logIgnoreClientPackets.add(ClientPacket.CS_LEAVE_WORLD);
        logIgnoreClientPackets.add(ClientPacket.CS_LIST_CHARACTER);
        logIgnoreClientPackets.add(ClientPacket.CS_BROADCAST_VISUAL_OPTION);
        logIgnoreClientPackets.add(ClientPacket.CS_REFRESH_IN_CHARACTER_LIST);
        logIgnoreClientPackets.add(ClientPacket.CS_DELETE_CHARACTER);
    }

    private void initLogIgnoreServerPackets() {
        logIgnoreServerPackets.add(ServerPacket.X2_ENTER_WORLD_RESPONSE);
        logIgnoreServerPackets.add(ServerPacket.SC_RECONNECT_AUTH);
        logIgnoreServerPackets.add(ServerPacket.SC_INITIAL_CONFIG);
        logIgnoreServerPackets.add(ServerPacket.SC_CHARACTER_LIST);
        logIgnoreServerPackets.add(ServerPacket.SC_CHAT_SPAM_DELAY);
        logIgnoreServerPackets.add(ServerPacket.SC_ACCOUNT_WARNED);
        logIgnoreServerPackets.add(ServerPacket.SC_ACCOUNT_INFO);
        logIgnoreServerPackets.add(ServerPacket.SC_REFRESH_IN_CHARACTER_LIST);
        logIgnoreServerPackets.add(ServerPacket.SC_CHARACTER_CREATION_FAILED);
    }

    private void initLogIgnoreProxyPackets() {
        logIgnoreProxyPackets.add(ProxyPacket.PING);
        logIgnoreProxyPackets.add(ProxyPacket.PONG);
        logIgnoreProxyPackets.add(ProxyPacket.CHANGE_STATE);
        logIgnoreProxyPackets.add(ProxyPacket.FINISH_STATE);
        logIgnoreProxyPackets.add(ProxyPacket.SET_GAME_TYPE);
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
                switch (PacketLevel.getByLevel(byteBufUtil.readPacketLevel((ByteBuf) msg))) {
                    case _1 -> printClientPacket(ctx, (ByteBuf) msg);
                    case _2 -> printProxyPacket(ctx, (ByteBuf) msg);
                }
            } catch (Exception e) {
                ((ByteBuf) msg).readerIndex(0);
                byteBufUtil.printBuf((ByteBuf) msg);
                return;
            }
        }
        ctx.fireChannelRead(msg);
    }

    private void printClientPacket(ChannelHandlerContext ctx, ByteBuf msg) {
        ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        msg.readerIndex(0);
        if (!logIgnoreClientPackets.contains(clientPacket)) {
            logger.log(internalLevel, format(ctx, "READ", msg));
        }
    }

    private void printProxyPacket(ChannelHandlerContext ctx, ByteBuf msg) {
        ProxyPacket proxyPacket = ProxyPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        msg.readerIndex(0);
        if (!logIgnoreProxyPackets.contains(proxyPacket)) {
            logger.log(internalLevel, format(ctx, "READ", msg));
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!useIgnoreList) {
            super.write(ctx, msg, promise);
            return;
        }
        if (logger.isEnabled(internalLevel)) {
            ((ByteBuf) msg).readerIndex(2);
            switch (PacketLevel.getByLevel(byteBufUtil.readPacketLevel((ByteBuf) msg))) {
                case _1 -> writeServerPacket(ctx, (ByteBuf) msg);
                case _2 -> writeProxyPacket(ctx, (ByteBuf) msg);
            }
        }
        ctx.write(msg, promise);
    }

    private void writeServerPacket(ChannelHandlerContext ctx, ByteBuf msg) {
        ServerPacket serverPacket = ServerPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        msg.readerIndex(0);
        if (!logIgnoreServerPackets.contains(serverPacket)) {
            logger.log(internalLevel, format(ctx, "WRITE", msg));
        }
    }

    private void writeProxyPacket(ChannelHandlerContext ctx, ByteBuf msg) {
        ProxyPacket proxyPacket = ProxyPacket.getByRawOpcode(byteBufUtil.readOpcode(msg));
        msg.readerIndex(0);
        if (!logIgnoreProxyPackets.contains(proxyPacket)) {
            logger.log(internalLevel, format(ctx, "WRITE", msg));
        }
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
