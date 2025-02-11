package com.aaemu.stream.service.netty;

import com.aaemu.stream.service.exception.StreamServerException;
import com.aaemu.stream.service.netty.config.ChannelConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author Shannon
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class StreamServer {
    private final Class<? extends ServerSocketChannel> serverSocketChannel;
    private final EventLoopGroup parentEventGroup;
    private final EventLoopGroup childEventGroup;
    private final ChannelConfig channelConfig;

    @Value("${stream_server.port}")
    private int port;

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(parentEventGroup, childEventGroup)
                    .channel(serverSocketChannel)
                    .localAddress(new InetSocketAddress(port))
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(channelConfig);
            ChannelFuture bindFuture = bootstrap.bind().sync();
            if (bindFuture.isSuccess()) {
                log.info("Start stream server on port: {}", port);
            }
        } catch (Exception e) {
            throw new StreamServerException(e);
        }
    }

    public void stop() {
        childEventGroup.shutdownGracefully();
        parentEventGroup.shutdownGracefully();
        log.info("Stop stream server");
    }
}
