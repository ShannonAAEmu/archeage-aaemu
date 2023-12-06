package com.aaemu.login;

import com.aaemu.login.handler.ExceptionHandler;
import com.aaemu.login.handler.PacketCodec;
import com.aaemu.login.handler.ProcessingHandler;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginServer {
    private final Class<? extends ServerSocketChannel> serverSocketChannel;
    private final ProcessingHandler processingHandler;
    private final EventExecutorGroup eventExecutor;
    private final EventLoopGroup parentEventGroup;
    private final EventLoopGroup childEventGroup;
    private final ByteBufUtil byteBufUtil;
    private final Map<Channel, String> accountMap;

    @Value("${login.port}")
    private int port;

    @PostConstruct
    public void start() throws InterruptedException {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(parentEventGroup, childEventGroup)
                    .channel(serverSocketChannel)
                    .localAddress(port)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(@NonNull SocketChannel channel) {
                            channel.pipeline().addLast("exception", new ExceptionHandler(accountMap));
                            channel.pipeline().addLast("logger", new LoggingHandler(LogLevel.INFO));
                            channel.pipeline().addLast("codec", new PacketCodec(byteBufUtil));
                            channel.pipeline().addLast(eventExecutor, processingHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();
            if (future.isSuccess()) {
                log.info("Login server started on port: {}", port);
            }
            future.channel().closeFuture().sync();
        } finally {
            parentEventGroup.shutdownGracefully();
            childEventGroup.shutdownGracefully();
            log.info("Stop login server");
        }
    }

    @PreDestroy
    public void shutdownGracefully() {
        parentEventGroup.shutdownGracefully();
        childEventGroup.shutdownGracefully();
        log.info("Stop login server");
    }

}
