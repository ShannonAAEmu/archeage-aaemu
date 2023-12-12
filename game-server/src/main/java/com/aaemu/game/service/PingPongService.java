package com.aaemu.game.service;

import com.aaemu.game.service.dto.packet.proxy.Ping;
import io.netty.channel.Channel;

public interface PingPongService {

    void pong(Ping packet, Channel channel);
}
