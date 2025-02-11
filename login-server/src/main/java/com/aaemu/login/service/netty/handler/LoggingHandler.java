package com.aaemu.login.service.netty.handler;

import com.aaemu.login.service.enums.ClientPacket;
import com.aaemu.login.service.enums.ServerPacket;
import com.aaemu.login.service.util.ByteBufUtils;
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
        logIgnoreClientPackets.add(ClientPacket.CARequestAuth);
        logIgnoreClientPackets.add(ClientPacket.CARequestAuthTencent);
        logIgnoreClientPackets.add(ClientPacket.CAChallengeResponse);
        logIgnoreClientPackets.add(ClientPacket.CAChallengeResponse2);
        logIgnoreClientPackets.add(ClientPacket.CAOtpNumber);
        logIgnoreClientPackets.add(ClientPacket.CAPcCertNumber);
        logIgnoreClientPackets.add(ClientPacket.CAListWorld);
        logIgnoreClientPackets.add(ClientPacket.CAEnterWorld);
        logIgnoreClientPackets.add(ClientPacket.CACancelEnterWorld);
    }

    private void initLogIgnoreServerPackets() {
        logIgnoreServerPackets.add(ServerPacket.ACJoinResponse);
        logIgnoreServerPackets.add(ServerPacket.ACChallenge);
        logIgnoreServerPackets.add(ServerPacket.ACAuthResponse);
        logIgnoreServerPackets.add(ServerPacket.ACChallenge2);
        logIgnoreServerPackets.add(ServerPacket.ACEnterOtp);
        logIgnoreServerPackets.add(ServerPacket.ACShowArs);
        logIgnoreServerPackets.add(ServerPacket.ACEnterPcCert);
        logIgnoreServerPackets.add(ServerPacket.ACWorldList);
        logIgnoreServerPackets.add(ServerPacket.ACWorldQueue);
        logIgnoreServerPackets.add(ServerPacket.ACWorldCookie);
        logIgnoreServerPackets.add(ServerPacket.ACEnterWorldDenied);
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
