package com.aaemu.login.service;

import io.netty.channel.Channel;

public interface LoginService {

    void rejectLogin(Channel channel, int reason, String msg);

    void rejectWarnedAccount(Channel channel, int source, String msg);

    boolean isValidAccount(Channel channel);

    void allowLogin(Channel channel);
}
