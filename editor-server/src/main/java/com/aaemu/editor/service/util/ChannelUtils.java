package com.aaemu.editor.service.util;

import com.aaemu.editor.service.exception.EditorServerException;
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
            throw new EditorServerException("TCP channel is null");
        }
        if (Objects.isNull(channel.remoteAddress())) {
            throw new EditorServerException("TCP channel without remote address");
        }
        return channel;
    }
}
