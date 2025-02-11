package com.aaemu.login.service.netty;

import com.aaemu.login.service.exception.LoginServerException;
import com.aaemu.login.service.netty.config.ChannelConfig;
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
public class LoginServer {
    private final Class<? extends ServerSocketChannel> serverSocketChannel;
    private final EventLoopGroup parentEventGroup;
    private final EventLoopGroup childEventGroup;
    private final ChannelConfig channelConfig;

    @Value("${login_server.port}")
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
                log.info("Start login server on port: {}", port);
            }
        } catch (Exception e) {
            throw new LoginServerException(e);
        }
    }

    public void stop() {
        childEventGroup.shutdownGracefully();
        parentEventGroup.shutdownGracefully();
        log.info("Stop login server");
    }
}
