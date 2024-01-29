package com.aaemu.stream.client;

import com.aaemu.stream.service.dto.client.StreamAccountDto;

public interface GameServer {

    void sendAuth(StreamAccountDto streamAccountDto);
}
