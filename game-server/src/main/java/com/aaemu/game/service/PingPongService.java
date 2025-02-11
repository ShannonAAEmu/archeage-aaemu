package com.aaemu.game.service;

import com.aaemu.game.service.dto.packet.proxy.Ping;

/**
 * @author Shannon
 */
public interface PingPongService {

    void pong(Ping packet);
}
