package com.aaemu.login.service.netty.handler;

import com.aaemu.login.service.enums.packet.ClientPacket;
import com.aaemu.login.service.enums.packet.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtil;
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
        logIgnoreClientPackets.add(ClientPacket.CA_REQUEST_AUTH);
        logIgnoreClientPackets.add(ClientPacket.CA_REQUEST_AUTH_TENCENT);
        logIgnoreClientPackets.add(ClientPacket.CA_CHALLENGE_RESPONSE);
        logIgnoreClientPackets.add(ClientPacket.CA_CHALLENGE_RESPONSE_2);
        logIgnoreClientPackets.add(ClientPacket.CA_OTP_NUMBER);
        logIgnoreClientPackets.add(ClientPacket.CA_TEST_ARS);
        logIgnoreClientPackets.add(ClientPacket.CA_PC_CERT_NUMBER);
        logIgnoreClientPackets.add(ClientPacket.CA_LIST_WORLD);
        logIgnoreClientPackets.add(ClientPacket.CA_ENTER_WORLD);
        logIgnoreClientPackets.add(ClientPacket.CA_CANCEL_ENTER_WORLD);
        logIgnoreClientPackets.add(ClientPacket.CA_REQUEST_RECONNECT);
    }

    private void initLogIgnoreServerPackets() {
        logIgnoreServerPackets.add(ServerPacket.AC_JOIN_RESPONSE);
        logIgnoreServerPackets.add(ServerPacket.AC_CHALLENGE);
        logIgnoreServerPackets.add(ServerPacket.AC_AUTH_RESPONSE);
        logIgnoreServerPackets.add(ServerPacket.AC_CHALLENGE_2);
        logIgnoreServerPackets.add(ServerPacket.AC_ENTER_OTP);
        logIgnoreServerPackets.add(ServerPacket.AC_SHOW_ARS);
        logIgnoreServerPackets.add(ServerPacket.AC_ENTER_PC_CERT);
        logIgnoreServerPackets.add(ServerPacket.AC_WORLD_LIST);
        logIgnoreServerPackets.add(ServerPacket.AC_WORLD_QUEUE);
        logIgnoreServerPackets.add(ServerPacket.AC_WORLD_COOKIE);
        logIgnoreServerPackets.add(ServerPacket.AC_ENTER_WORLD_DENIED);
        logIgnoreServerPackets.add(ServerPacket.AC_LOGIN_DENIED);
        logIgnoreServerPackets.add(ServerPacket.AC_ACCOUNT_WARNED);
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
            ClientPacket clientPacket = ClientPacket.getByRawOpcode(byteBufUtil.readOpcode(((ByteBuf) msg)));
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
