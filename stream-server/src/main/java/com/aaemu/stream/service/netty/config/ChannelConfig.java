package com.aaemu.stream.service.netty.config;


import com.aaemu.stream.service.model.Account;
import com.aaemu.stream.service.netty.decoder.LengthFieldBasedFrameDecoder;
import com.aaemu.stream.service.netty.handler.ClientHandler;
import com.aaemu.stream.service.netty.handler.CodecHandler;
import com.aaemu.stream.service.netty.handler.ExceptionHandler;
import com.aaemu.stream.service.netty.handler.LoggingHandler;
import com.aaemu.stream.service.util.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Shannon
 */
@Component
@RequiredArgsConstructor
public class ChannelConfig extends ChannelInitializer<SocketChannel> {
    private final Map<Channel, Account> accountMap;
    private final ClientHandler clientHandler;
    private final ByteBufUtil byteBufUtil;

    @Value("${stream_server.hex_logger.active}")
    private boolean useHexLog;

    @Value("${stream_server.hex_logger.use_ignore_list}")
    private boolean useIgnoreList;

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast("exception", new ExceptionHandler(accountMap));
        ch.pipeline().addLast("framer", new LengthFieldBasedFrameDecoder());
        if (useHexLog) {
            ch.pipeline().addLast("logger", new LoggingHandler(LogLevel.INFO, byteBufUtil, useIgnoreList));
        }
        ch.pipeline().addLast("codec", new CodecHandler(byteBufUtil));
        ch.pipeline().addLast("client_handler", clientHandler);
    }
}
