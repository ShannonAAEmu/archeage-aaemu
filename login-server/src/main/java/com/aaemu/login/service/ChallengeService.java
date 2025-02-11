package com.aaemu.login.service;

import com.aaemu.login.service.dto.packet.client.CAChallengeResponse;
import com.aaemu.login.service.dto.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.dto.packet.client.CAOtpNumber;
import com.aaemu.login.service.dto.packet.client.CAPcCertNumber;

public interface ChallengeService {

    void challenge(CAChallengeResponse packet);

    void challenge(CAChallengeResponse2 packet);

    void processOneTimePassword(CAOtpNumber packet);

    void processPcCertificate(CAPcCertNumber packet);
}
