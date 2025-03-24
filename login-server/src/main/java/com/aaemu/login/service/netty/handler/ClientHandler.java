package com.aaemu.login.service.netty.handler;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.ChallengeService;
import com.aaemu.login.service.WorldService;
import com.aaemu.login.service.dto.packet.ClientPacket;
import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.client.CATestArs;
import com.aaemu.login.service.exception.PacketException;
import com.aaemu.login.service.model.Account;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author Shannon
 */
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Log4j2
public class ClientHandler extends SimpleChannelInboundHandler<ClientPacket> {
    private static final String INACTIVE_TCP_CHANNEL = "Channel {} INACTIVE for I/O [-]{}";
    private static final String ACTIVE_TCP_CHANNEL = "Channel {} ACTIVE for I/O [+]{}";
    private static final String DISCONNECT = "Disconnect: {}";
    private final ChallengeService challengeService;
    private final Map<Channel, Account> accountMap;
    private final WorldService worldService;
    private final AuthService authService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info(ACTIVE_TCP_CHANNEL, ctx.channel().remoteAddress().toString(), System.lineSeparator());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Account removedAccount = accountMap.remove(ctx.channel());
        ctx.channel().close();
        log.info(DISCONNECT, Objects.isNull(removedAccount) ? ctx.channel().remoteAddress().toString() : removedAccount);
        log.info(INACTIVE_TCP_CHANNEL, ctx.channel().remoteAddress().toString(), System.lineSeparator());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket clientPacket) {
        switch (clientPacket) {
            case CARequestAuth packet -> authService.auth(packet);
            case CAChallengeResponse packet -> challengeService.receive(packet);
            case CAOtpNumber packet -> challengeService.receive(packet);
            case CATestArs packet -> challengeService.receive(packet);
            case CAPcCertNumber packet -> challengeService.receive(packet);
            case CAListWorld packet -> worldService.sendWorldList(packet.getChannel());
            case CAEnterWorld packet -> worldService.enterToWorld(packet.getChannel(), packet.getWid());
            case CACancelEnterWorld packet -> worldService.cancelEnterWorld(packet);
            case CARequestReconnect packet -> authService.requestReconnect(packet);
            default -> throw new PacketException("Not implemented: " + clientPacket);
        }
    }
}
