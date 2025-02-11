package com.aaemu.login.service;

import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.client.ServerDto;
import com.aaemu.login.service.model.Account;

/**
 * @author Shannon
 */
public interface GameServerService {

    ServerDto getServerInfo(Account account);

    QueueStatusDto getQueueStatus(int worldId, Account account);
}
