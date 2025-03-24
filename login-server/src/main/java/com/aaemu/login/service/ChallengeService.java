package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CATestArs;
import io.netty.channel.Channel;

/**
 * @author Shannon
 */
public interface ChallengeService {

    void send(Channel channel);

    void receive(CAChallengeResponse packet);

    void receive(CAOtpNumber packet);

    void receive(CATestArs packet);

    void receive(CAPcCertNumber packet);
}
