package com.aaemu.zone.service;

import com.aaemu.zone.service.dto.packet.client.ZWJoinPacket;

/**
 * @author Shannon
 */
public interface JoinService {

    void join(ZWJoinPacket packet);
}
