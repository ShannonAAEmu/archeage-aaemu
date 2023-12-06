package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;
import io.netty.channel.Channel;

public interface ChallengeService {

    void challenge(CAChallengeResponse challengeResponse, Channel channel);

    void challenge(CAChallengeResponse2 challengeResponse2, Channel channel);

    void processOneTimePassword(CAOtpNumber caOtpNumber, Channel channel);

    void processPcCertificate(CAPcCertNumber caPcCertNumber, Channel channel);
}
