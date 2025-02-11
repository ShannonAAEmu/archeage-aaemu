package com.aaemu.game.service;

import com.aaemu.game.service.dto.packet.client.CSBroadcastVisualOption;
import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.client.CSLeaveWorld;
import com.aaemu.game.service.dto.packet.client.CSRefreshInCharacterList;
import io.netty.channel.Channel;

/**
 * @author Shannon
 */
public interface GameServerService {

    void broadcastVisualOption(CSBroadcastVisualOption packet);

    void refreshInCharacterList(CSRefreshInCharacterList packet);

    void sendAccountWarned(Channel channel, int source, String msg);

    void leaveWorld(CSLeaveWorld packet);

    void createCharacter(CSCreateCharacter packet);
}
