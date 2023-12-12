package com.aaemu.stream;

import com.aaemu.stream.handler.CodecHandler;
import com.aaemu.stream.handler.ExceptionHandler;
import com.aaemu.stream.handler.ProcessingHandler;
import com.aaemu.stream.util.ByteBufUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class StreamServer {

    private final ProcessingHandler processingHandler;
    private final Map<Channel, Long> accountMap;
    private final ByteBufUtil byteBufUtil;

    @Value("${stream.threads}")
    private int eventExecutorGroupThreads;

    @Value("${stream.port}")
    private int port;

    @Value("${logger.active}")
    private boolean isActiveLog;

    @Value("${logger.level}")
    private String logLevel;

    @PostConstruct
    public void start() {
        Thread thread = Thread.startVirtualThread(() -> {
            EventLoopGroup parentEventGroup;
            EventLoopGroup childEventGroup;
            if (Epoll.isAvailable()) {
                parentEventGroup = new EpollEventLoopGroup();
            } else if (KQueue.isAvailable()) {
                parentEventGroup = new KQueueEventLoopGroup();
            } else {
                parentEventGroup = new NioEventLoopGroup();
            }
            if (Epoll.isAvailable()) {
                childEventGroup = new EpollEventLoopGroup();
            } else if (KQueue.isAvailable()) {
                childEventGroup = new KQueueEventLoopGroup();
            } else {
                childEventGroup = new NioEventLoopGroup();
            }
            Class<? extends ServerSocketChannel> serverSocketChannel;
            if (Epoll.isAvailable()) {
                serverSocketChannel = EpollServerSocketChannel.class;
            } else if (KQueue.isAvailable()) {
                serverSocketChannel = KQueueServerSocketChannel.class;
            } else {
                serverSocketChannel = NioServerSocketChannel.class;
            }
            EventExecutorGroup eventExecutor = new DefaultEventExecutorGroup(eventExecutorGroupThreads);
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
                                if (isActiveLog) {
                                    channel.pipeline().addLast("logger", new LoggingHandler(LogLevel.valueOf(logLevel)));
                                }
                                channel.pipeline().addLast("codec", new CodecHandler(byteBufUtil));
                                channel.pipeline().addLast(eventExecutor, processingHandler);
                            }
                        });
                ChannelFuture future = bootstrap.bind().sync();
                if (future.isSuccess()) {
                    log.info("Stream server started on port: {}", port);
                }
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                parentEventGroup.shutdownGracefully();
                childEventGroup.shutdownGracefully();
                log.info("Stop stream server");
            }
        });
        thread.setName("netty-stream");
    }
}
