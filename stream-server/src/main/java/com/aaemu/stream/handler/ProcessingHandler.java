package com.aaemu.stream.handler;

import com.aaemu.stream.service.AuthService;
import com.aaemu.stream.service.dto.packet.ClientPacket;
import com.aaemu.stream.service.dto.packet.client.CTJoin;
import com.aaemu.stream.service.exception.PacketException;
import com.aaemu.stream.service.model.StreamAccount;
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
    private final Map<Channel, StreamAccount> accountMap;
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
    protected void channelRead0(ChannelHandlerContext ctx, ClientPacket clientPacket) {
        switch (clientPacket) {
            case CTJoin packet -> authService.enterWorld(packet, ctx.channel());
            default -> throw new PacketException(String.format("Unknown packet for processing: %s", clientPacket));
        }
    }
}
