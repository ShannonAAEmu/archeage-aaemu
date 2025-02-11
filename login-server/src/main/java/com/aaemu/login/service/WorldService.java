package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import io.netty.channel.Channel;

public interface WorldService {

    void requestList(Channel channel);

    void requestEnter(Channel channel, int worldId);

    void cancelEnterWorld(CACancelEnterWorld packet);

    void requestReconnect(CARequestReconnect packet);
}
