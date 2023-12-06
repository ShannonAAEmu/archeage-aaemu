package com.aaemu.login.handler;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.entity.packet.Packet;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.entity.packet.client.CAListWorld;
import com.aaemu.login.service.entity.packet.client.CAOtpNumber;
import com.aaemu.login.service.entity.packet.client.CARequestAuth;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ProcessingHandler extends SimpleChannelInboundHandler<Packet> {
    private final AuthService authService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        if (packet instanceof CARequestAuth requestAuthPacket) {
            authService.requestAuth(requestAuthPacket, ctx.channel());
        } else if (packet instanceof CAChallengeResponse challengeResponse) {
            authService.firstChallengeResponse(challengeResponse, ctx.channel());
        } else if (packet instanceof CAChallengeResponse2 challengeResponse2) {
            authService.secondChallengeResponse(challengeResponse2, ctx.channel());
        } else if (packet instanceof CAOtpNumber caOtpNumber) {
            authService.otpProcess(caOtpNumber, ctx.channel());
        } else if (packet instanceof CAListWorld listWorld) {
            authService.listWorldProcess(listWorld, ctx.channel());
        }
    }

}
