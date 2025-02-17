package com.aaemu.login.service;

import com.aaemu.login.service.dto.client.AccountFutureSet;
import com.aaemu.login.service.dto.client.QueueStatus;
import com.aaemu.login.service.dto.client.ServerInfo;
import com.aaemu.login.service.model.Account;

/**
 * @author Shannon
 */
public interface GameServerService {

    AccountFutureSet getAccountFutureSet();

    ServerInfo getServerInfo(Account account);

    QueueStatus getQueueStatus(Account account);
}
