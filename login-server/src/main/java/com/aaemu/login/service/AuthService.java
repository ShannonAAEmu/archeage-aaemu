package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CARequestAuth;
import com.aaemu.login.service.dto.packet.client.CARequestReconnect;

public interface AuthService {

    void auth(CARequestAuth packet);

    void requestReconnect(CARequestReconnect packet);
}
