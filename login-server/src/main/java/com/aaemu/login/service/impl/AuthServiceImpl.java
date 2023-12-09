package com.aaemu.login.service.impl;

import com.aaemu.login.service.AuthService;
import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import com.aaemu.login.service.dto.packet.server.ACChallenge;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ByteBufUtil byteBufUtil;
    private final Map<Channel, String> accountMap;

    private void sendChallenge(Channel channel) {
        ACChallenge acChallenge = new ACChallenge();
        acChallenge.setSalt(0);
        acChallenge.setCh(0);
        channel.writeAndFlush(acChallenge.build(byteBufUtil));
    }

    @Override
    public void requestAuth(CARequestAuth packet, Channel channel) {
        log.info("Request auth from account: {}", packet.getAccount());
        accountMap.replace(channel, packet.getAccount());
        sendChallenge(channel);
    }

    @Override
    public void requestReconnect(CARequestReconnect packet, Channel channel) {
        log.info("Request reconnect from account id: {}, world id: {}", packet.getAid(), packet.getWid());
        accountMap.replace(channel, String.valueOf(packet.getAid()));
        sendChallenge(channel);
    }
}
