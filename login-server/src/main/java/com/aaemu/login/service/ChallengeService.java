package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import com.aaemu.login.service.dto.packet.client.CATestArs;
import io.netty.channel.Channel;

public interface ChallengeService {

    void sendChallenge(Channel channel);

    void sendChallenge2(Channel channel);

    void challenge(CAChallengeResponse packet);

    void challenge(CAChallengeResponse2 packet);

    void processOneTimePassword(CAOtpNumber packet);

    void testArs(CATestArs packet);

    void processPcCertificate(CAPcCertNumber packet);
}
