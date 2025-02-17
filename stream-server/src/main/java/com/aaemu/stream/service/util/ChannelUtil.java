package com.aaemu.stream.service.util;

import com.aaemu.stream.service.exception.StreamServerException;
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
            throw new StreamServerException("TCP channel is null");
        }
        if (Objects.isNull(channel.remoteAddress())) {
            throw new StreamServerException("TCP channel without remote address");
        }
        return channel;
    }
}
