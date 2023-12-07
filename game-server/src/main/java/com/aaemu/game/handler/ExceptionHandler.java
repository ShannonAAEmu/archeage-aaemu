package com.aaemu.game.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;
import java.util.Map;

@RequiredArgsConstructor
@ChannelHandler.Sharable
@Slf4j
public class ExceptionHandler extends ChannelDuplexHandler {
    private final Map<Channel, String> accountMap;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SocketException socketException) {
            if (socketException.getMessage().equals("Connection reset")) {
                if (accountMap.get(ctx.channel()).chars().allMatch(Character::isDigit)) {
                    log.warn("Disconnect account id: {}", accountMap.get(ctx.channel()));
                } else {
                    log.warn("Disconnect account: {}", accountMap.get(ctx.channel()));
                }
                accountMap.remove(ctx.channel());
            } else {
                super.exceptionCaught(ctx, cause);
            }
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
