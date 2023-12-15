package com.aaemu.game.handler;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.GameService;
import com.aaemu.game.service.PingPongService;
import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.exception.PacketException;
import com.aaemu.game.service.model.Account;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ProcessingHandler extends SimpleChannelInboundHandler<ClientPacket> {
    private final PingPongService pingPongService;
    private final Map<Channel, Account> accountMap;
    private final AuthService authService;
    private final GameService gameService;

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
            case Ping packet -> pingPongService.pong(packet, ctx.channel());
            case X2EnterWorld packet -> authService.firstStepEnterWorld(packet, ctx.channel());
            case FinishState packet -> authService.secondStepEnterWorld(packet, ctx.channel());
            case CSListCharacter packet -> authService.sendCharacterList(packet, ctx.channel());
            case CSRefreshInCharacterList packet -> pingPongService.refreshCharacterList(packet, ctx.channel());
            case CSBroadcastVisualOption packet -> gameService.setBroadcastVisualOption(packet, ctx.channel());
            case CSCreateCharacter packet -> gameService.createCharacter(packet, ctx.channel());
            default -> throw new PacketException(String.format("Unknown packet for processing: %s", clientPacket));
        }
    }
}
