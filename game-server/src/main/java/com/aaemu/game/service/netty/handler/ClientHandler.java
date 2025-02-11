package com.aaemu.game.service.netty.handler;

import com.aaemu.game.service.AuthService;
import com.aaemu.game.service.GameServerService;
import com.aaemu.game.service.PingPongService;
import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSLeaveWorld;
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
    private final GameServerService gameServerService;
    private final Map<Channel, Account> accountMap;
    private final PingPongService pingPongService;
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
            case Ping packet -> pingPongService.pong(packet);
            case X2EnterWorld packet -> authService.enterWorld(packet);
            case FinishState packet -> authService.finishState(packet);
            case CSListCharacter packet -> authService.listCharacter(packet);
            case CSBroadcastVisualOption packet -> gameServerService.broadcastVisualOption(packet);
            case CSRefreshInCharacterList packet -> gameServerService.refreshInCharacterList(packet);
            case CSLeaveWorld packet -> gameServerService.leaveWorld(packet);
            case CSCreateCharacter packet -> gameServerService.createCharacter(packet);
            default -> throw new PacketException("Unknown packet for processing: " + clientPacket);
        }
    }
}
