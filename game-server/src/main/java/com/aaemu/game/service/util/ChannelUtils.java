package com.aaemu.game.service.util;

import com.aaemu.game.service.exception.GameServerException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Objects;

/**
 * @author Shannon
 */
public class ChannelUtils {

    public static Channel getChannel(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        if (Objects.isNull(channel)) {
            throw new GameServerException("TCP channel is null");
        }
        if (Objects.isNull(channel.remoteAddress())) {
            throw new GameServerException("TCP channel without remote address");
        }
        return channel;
    }
}
