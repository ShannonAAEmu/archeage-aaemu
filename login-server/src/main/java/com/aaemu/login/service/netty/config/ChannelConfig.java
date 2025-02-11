package com.aaemu.login.service.netty.config;

import com.aaemu.login.service.model.Account;
import com.aaemu.login.service.netty.decoder.LengthFieldBasedFrameDecoder;
import com.aaemu.login.service.netty.handler.ClientHandler;
import com.aaemu.login.service.netty.handler.CodecHandler;
import com.aaemu.login.service.netty.handler.ExceptionHandler;
import com.aaemu.login.service.netty.handler.LoggingHandler;
import com.aaemu.login.service.util.ByteBufUtils;
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
    private final ByteBufUtils byteBufUtils;

    @Value("${login_server.hex_logger.active}")
    private boolean useHexLog;

    @Value("${login_server.hex_logger.use_ignore_list}")
    private boolean useIgnoreList;

    @Value("${login_server.auth.editor_mode.active}")
    private boolean isEditorMode;

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast("exception", new ExceptionHandler(accountMap));
        ch.pipeline().addLast("framer", new LengthFieldBasedFrameDecoder());
        if (useHexLog) {
            ch.pipeline().addLast("logger", new LoggingHandler(LogLevel.INFO, byteBufUtils, useIgnoreList));
        }
        ch.pipeline().addLast("codec", new CodecHandler(byteBufUtils, isEditorMode));
        ch.pipeline().addLast("client_handler", clientHandler);
    }
}
