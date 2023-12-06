package com.aaemu.login.service;

import io.netty.channel.Channel;

public interface LoginService {

    void allowLogin(Channel channel);

    void rejectLogin(Channel channel, int reason, String msg);

    void rejectWarnedAccount(Channel channel, int source, String msg);
}
