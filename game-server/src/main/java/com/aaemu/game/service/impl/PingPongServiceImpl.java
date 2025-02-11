package com.aaemu.game.service.impl;

import com.aaemu.game.service.PingPongService;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.dto.packet.proxy.Pong;
import com.aaemu.game.service.util.ByteBufUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Shannon
 */
@Service
@RequiredArgsConstructor
public class PingPongServiceImpl implements PingPongService {
    private final ByteBufUtils byteBufUtils;

    @Override
    public void pong(Ping packet) {
        Pong pong = new Pong();
        pong.setTm(packet.getTm());
        pong.setWhen(packet.getWhen());
        pong.setElapsed(0);
        pong.setRemote(Instant.EPOCH.getEpochSecond() * 10000);
        pong.setLocal(packet.getLocal());
        pong.setWorld(Instant.now().getNano());
        packet.getChannel().writeAndFlush(pong.build(byteBufUtils));
    }
}
