package com.aaemu.login;

import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class LoginServerConfig {

    @Value("${app.threads}")
    private int eventExecutorGroupThreads;

    @Bean
    public ByteBufUtil byteBufUtil() {
        return new ByteBufUtil(true, StandardCharsets.US_ASCII);
    }

    @Bean
    public EventLoopGroup parentEventGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        } else if (KQueue.isAvailable()) {
            return new KQueueEventLoopGroup();
        }
        return new NioEventLoopGroup();
    }

    @Bean
    public EventLoopGroup childEventGroup() {
        if (Epoll.isAvailable()) {
            return new EpollEventLoopGroup();
        } else if (KQueue.isAvailable()) {
            return new KQueueEventLoopGroup();
        }
        return new NioEventLoopGroup();
    }

    @Bean
    public Class<? extends ServerSocketChannel> serverSocketChannel() {
        if (Epoll.isAvailable()) {
            return EpollServerSocketChannel.class;
        } else if (KQueue.isAvailable()) {
            return KQueueServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }

    @Bean
    public EventExecutorGroup eventExecutor() {
        return new DefaultEventExecutorGroup(eventExecutorGroupThreads);
    }

}
