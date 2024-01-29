package com.aaemu.login.client;

import com.aaemu.login.service.dto.client.AddressDto;
import com.aaemu.login.service.dto.client.CharacterDto;
import com.aaemu.login.service.dto.client.QueueAccountDto;
import com.aaemu.login.service.dto.client.QueueStatusDto;
import com.aaemu.login.service.dto.client.ServerDto;

import java.util.List;

public interface GameServer {

    AddressDto getAddress();

    List<ServerDto> getServerList();

    List<CharacterDto> getCharacterList(QueueAccountDto queueAccountDto);

    Boolean hasQueue(int worldId);

    QueueStatusDto getQueueStatus(int worldId, QueueAccountDto queueAccountDto);
}
