package com.aaemu.game.service.netty.config;


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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Shannon
 */
@Configuration
public class ServerConfig {

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
}
