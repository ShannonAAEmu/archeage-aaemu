package com.aaemu.game.service.impl;

import com.aaemu.game.service.PingPongService;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import com.aaemu.game.service.dto.packet.proxy.Ping;
import com.aaemu.game.service.dto.packet.proxy.Pong;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class PingPongServiceImpl implements PingPongService {
    private final ByteBufUtil byteBufUtil;

    @Override
    public void pong(Ping packet, Channel channel) {
        Pong pong = new Pong();
        pong.setTm(packet.getTm());
        pong.setWhen(packet.getWhen());
        pong.setElapsed(0);
        pong.setRemote(Instant.EPOCH.getEpochSecond() * 1000);
        pong.setLocal(packet.getLocal());
        pong.setWorld(Instant.now().getNano());
        channel.writeAndFlush(pong.build(byteBufUtil));
    }

    @Override
    public void refreshCharacterList(CSRefreshInCharacterList packet, Channel channel) {
        // TODO drop fix
        log.warn("Refresh character list packet drop client. Need fix this.");
//        SCRefreshInCharacterList refreshInCharacterList = new SCRefreshInCharacterList();
//        List<Byte> conList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            conList.add((byte) 0);
//        }
//        refreshInCharacterList.setCon(conList);
//        channel.writeAndFlush(refreshInCharacterList.build(byteBufUtil));
    }
}
