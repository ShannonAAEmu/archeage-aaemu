package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CACancelEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAEnterWorld;
import com.aaemu.login.service.dto.packet.client.CAListWorld;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;
import io.netty.channel.Channel;

public interface WorldService {

    void requestList(CAListWorld listWorld, Channel channel);

    void enterWorld(CAEnterWorld enterWorld, Channel channel);

    void cancelEnterWorld(CACancelEnterWorld cancelEnterWorld, Channel channel);

    void requestReconnect(CARequestReconnect requestReconnect, Channel channel);
}
