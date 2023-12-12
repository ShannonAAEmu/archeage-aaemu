package com.aaemu.game.handler;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.PingPongService;
import com.aaemu.game.service.dto.packet.Packet;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.exception.PacketException;
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
    private final PingPongService pingPongService;
    private final Map<Channel, Long> accountMap;
    private final AuthService authService;

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
    protected void channelRead0(ChannelHandlerContext ctx, Packet clientPacket) {
        switch (clientPacket) {
            case Ping packet -> pingPongService.pong(packet, ctx.channel());
            case X2EnterWorld packet -> authService.enterWorld(packet, ctx.channel());
            case FinishState packet -> authService.enterWorld(packet, ctx.channel());
            case CSListCharacter packet -> authService.sendListCharacter(packet, ctx.channel());
            default -> throw new PacketException(String.format("Unknown packet for processing: %s", clientPacket));
        }
    }
}
