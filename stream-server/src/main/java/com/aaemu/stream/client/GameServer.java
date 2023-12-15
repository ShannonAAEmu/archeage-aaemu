package com.aaemu.stream.client;

import com.aaemu.stream.service.dto.client.CharacterDto;

public interface GameServer {

    void sendAuth(CharacterDto characterDto);
}
