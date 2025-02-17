package com.aaemu.login.service.util;

import com.aaemu.login.service.exception.LoginServerException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Objects;

/**
 * @author Shannon
 */
public class ChannelUtil {

    public static Channel getChannel(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        if (Objects.isNull(channel)) {
            throw new LoginServerException("TCP channel is null");
        }
        if (Objects.isNull(channel.remoteAddress())) {
            throw new LoginServerException("TCP channel without remote address");
        }
        return channel;
    }
}
