package com.aaemu.login.service;

import com.aaemu.login.service.model.Account;
import io.netty.channel.Channel;

/**
 * @author Shannon
 */
public interface LoginService {

    boolean isPresentAccount(Account account);

    boolean isActiveAccount(Account account);

    void rejectLogin(Channel channel, int reason, String msg);

    void rejectWarnedAccount(Channel channel, int source, String msg);

    void allowLogin(Channel channel);
}
