package com.aaemu.editor.service.impl;

import com.aaemu.editor.service.AuthService;
import com.aaemu.editor.service.dto.packet.client.CELogin;
import com.aaemu.editor.service.dto.packet.server.ECLoginResponse;
import com.aaemu.editor.service.dto.packet.server.Ping;
import com.aaemu.editor.service.model.Account;
import com.aaemu.editor.service.util.ByteBufUtils;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
@EnableScheduling
@Log4j2
public class AuthServiceImpl implements AuthService {
    private final Map<Channel, Account> accountMap;
    private final ByteBufUtils byteBufUtils;

    @Override
    public void login(CELogin packet) {
        log.info("Login to editor server from: {}", packet.getAccountName());
        accountMap.put(packet.getChannel(), new Account(packet.getAccountName()));
        ECLoginResponse loginResponse = new ECLoginResponse();
        loginResponse.setReason(0);
        loginResponse.setFileServerPath("D:\\aa\\Bin32");
        packet.getChannel().writeAndFlush(loginResponse.build(byteBufUtils));
    }

    @Scheduled(fixedRateString = "${editor_server.ping_interval}")
    private void ping() {
        for (Map.Entry<Channel, Account> entry : accountMap.entrySet()) {
            entry.getKey().writeAndFlush(new Ping().build(byteBufUtils));
        }
    }
}
