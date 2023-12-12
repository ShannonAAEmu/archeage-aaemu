package com.aaemu.game.service;

import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;
import io.netty.channel.Channel;

public interface AuthService {

    void enterWorld(X2EnterWorld packet, Channel channel);

    void enterWorld(FinishState packet, Channel channel);

    void changeState(long accountId);

    void sendListCharacter(CSListCharacter packet, Channel channel);
}
