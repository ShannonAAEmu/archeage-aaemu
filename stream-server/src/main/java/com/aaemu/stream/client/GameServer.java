package com.aaemu.stream.client;

import com.aaemu.stream.service.dto.client.StreamCharacterDto;

public interface GameServer {

    void sendAuth(StreamCharacterDto characterDto);
}
