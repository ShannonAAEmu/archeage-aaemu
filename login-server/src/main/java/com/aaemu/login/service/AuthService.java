package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import io.netty.channel.Channel;

public interface AuthService {

    void requestAuth(CARequestAuth packet, Channel channel);

    void requestReconnect(CARequestReconnect packet, Channel channel);
}
