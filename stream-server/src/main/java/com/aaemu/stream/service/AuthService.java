package com.aaemu.stream.service;

import com.aaemu.stream.service.dto.packet.client.CTJoin;
import io.netty.channel.Channel;

public interface AuthService {

    void enterWorld(CTJoin packet, Channel channel);
}
