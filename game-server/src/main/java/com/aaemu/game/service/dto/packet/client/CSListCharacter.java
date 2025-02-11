package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Shannon
 */
@Data
@AllArgsConstructor
public class CSListCharacter implements ClientPacket {
    private final Channel channel;
}
