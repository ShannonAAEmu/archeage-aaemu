package com.aaemu.login.handler;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.packet.Packet;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
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
public class ProcessingHandler extends SimpleChannelInboundHandler<Packet> {
    private final Map<Channel, String> accountMap;
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
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        if (packet instanceof CARequestAuth requestAuthPacket) {
            authService.requestAuth(requestAuthPacket, ctx.channel());
        } else if (packet instanceof CAChallengeResponse challengeResponse) {
            challengeService.challenge(challengeResponse, ctx.channel());
        } else if (packet instanceof CAChallengeResponse2 challengeResponse2) {
            challengeService.challenge(challengeResponse2, ctx.channel());
        } else if (packet instanceof CAOtpNumber otpNumber) {
            challengeService.processOneTimePassword(otpNumber, ctx.channel());
        } else if (packet instanceof CAPcCertNumber pcCertNumber) {
            challengeService.processPcCertificate(pcCertNumber, ctx.channel());
        } else if (packet instanceof CAListWorld listWorld) {
            worldService.requestList(listWorld, ctx.channel());
        } else if (packet instanceof CAEnterWorld enterWorld) {
            worldService.enterWorld(enterWorld, ctx.channel());
        } else if (packet instanceof CACancelEnterWorld cancelEnterWorld) {
            worldService.cancelEnterWorld(cancelEnterWorld, ctx.channel());
        } else if (packet instanceof CARequestReconnect requestReconnect) {
            worldService.requestReconnect(requestReconnect, ctx.channel());
        }
    }
}
