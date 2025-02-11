package com.aaemu.editor.service.netty.handler;

import com.aaemu.editor.service.AuthService;
import com.aaemu.editor.service.dto.packet.ClientPacket;
import com.aaemu.editor.service.dto.packet.client.CELogin;
import com.aaemu.editor.service.exception.PacketException;
import com.aaemu.editor.service.model.Account;
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
    private final Map<Channel, Account> accountMap;
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
            case CELogin packet -> authService.login(packet);
            default -> throw new PacketException("Unknown packet for processing: " + clientPacket);
        }
    }
}
