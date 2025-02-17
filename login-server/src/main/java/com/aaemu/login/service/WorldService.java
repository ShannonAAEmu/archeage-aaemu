package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import io.netty.channel.Channel;

public interface WorldService {

    void sendWorldList(Channel channel);

    void enterToWorld(Channel channel, byte worldId);

    void cancelEnterWorld(CACancelEnterWorld packet);
}
