package com.aaemu.game.service;

import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import io.netty.channel.Channel;

public interface GameService {

    void setBroadcastVisualOption(CSBroadcastVisualOption packet, Channel channel);

    void createCharacter(CSCreateCharacter packet, Channel channel);

    void sendAccountWarned(Channel channel);
}
