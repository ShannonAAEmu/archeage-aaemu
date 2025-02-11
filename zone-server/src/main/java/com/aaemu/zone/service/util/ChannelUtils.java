package com.aaemu.zone.service.util;

import com.aaemu.zone.service.exception.ZoneServerException;
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
            throw new ZoneServerException("TCP channel is null");
        }
        if (Objects.isNull(channel.remoteAddress())) {
            throw new ZoneServerException("TCP channel without remote address");
        }
        return channel;
    }
}
