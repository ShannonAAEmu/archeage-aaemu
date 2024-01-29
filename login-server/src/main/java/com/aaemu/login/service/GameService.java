package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import io.netty.channel.Channel;

public interface GameService {

    void requestList(CAListWorld packet, Channel channel);

    void enterWorld(CAEnterWorld packet, Channel channel);

    void cancelEnterWorld(CACancelEnterWorld packet, Channel channel);
}
