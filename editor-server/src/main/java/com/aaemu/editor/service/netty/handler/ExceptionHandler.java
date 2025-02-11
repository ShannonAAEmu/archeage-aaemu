package com.aaemu.editor.service.netty.handler;

import com.aaemu.editor.service.model.Account;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@ChannelHandler.Sharable
@Slf4j
public class ExceptionHandler extends ChannelDuplexHandler {
    private final Map<Channel, Account> accountMap;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Account account = accountMap.remove(ctx.channel());
        if (cause instanceof SocketException socketException) {
            if (socketException.getMessage().equals("Connection reset")) {
                if (Objects.nonNull(account)) {
                    log.warn("Disconnect account: {}", account);
                } else {
                    log.warn("Disconnect client: {}", ctx.channel());
                }
            } else {
                log.warn("Force disconnect client");
                super.exceptionCaught(ctx, cause);
            }
        } else {
            log.warn("Force disconnect client");
            super.exceptionCaught(ctx, cause);
        }
    }
}
