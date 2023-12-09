package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import io.netty.channel.Channel;

public interface WorldService {

    void requestList(CAListWorld packet, Channel channel);

    void enterWorld(CAEnterWorld packet, Channel channel);

    void cancelEnterWorld(CACancelEnterWorld packet, Channel channel);

    void requestReconnect(CARequestReconnect packet, Channel channel);
}
