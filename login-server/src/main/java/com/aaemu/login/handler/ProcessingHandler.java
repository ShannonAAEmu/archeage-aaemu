package com.aaemu.login.handler;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.exception.PacketException;
import com.aaemu.login.service.model.Account;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Slf4j
public class ProcessingHandler extends SimpleChannelInboundHandler<ClientPacket> {
    private final Map<Channel, Account> accountMap;
    private final AuthService authService;
    private final ChallengeService challengeService;
    private final WorldService worldService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        accountMap.put(ctx.channel(), null);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        accountMap.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket clientPacket) {
        switch (clientPacket) {
            case CARequestAuth packet -> authService.requestAuth(packet, ctx.channel());
            case CAChallengeResponse packet -> challengeService.challenge(packet, ctx.channel());
            case CAChallengeResponse2 packet -> challengeService.challenge(packet, ctx.channel());
            case CAOtpNumber packet -> challengeService.processOneTimePassword(packet, ctx.channel());
            case CAPcCertNumber packet -> challengeService.processPcCertificate(packet, ctx.channel());
            case CAListWorld packet -> worldService.requestList(packet, ctx.channel());
            case CAEnterWorld packet -> worldService.enterWorld(packet, ctx.channel());
            case CACancelEnterWorld packet -> worldService.cancelEnterWorld(packet, ctx.channel());
            case CARequestReconnect packet -> worldService.requestReconnect(packet, ctx.channel());
            default -> throw new PacketException(String.format("Unknown packet for processing: %s", clientPacket));
        }
    }
}
