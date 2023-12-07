package com.aaemu.game.handler;

import com.aaemu.game.service.dto.packet.Packet;
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

    }
}
