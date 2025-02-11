package com.aaemu.stream.service;

import com.aaemu.stream.service.dto.packet.client.CTJoin;

/**
 * @author Shannon
 */
public interface AuthService {

    void join(CTJoin packet);
}
