package com.aaemu.login.service;

import com.aaemu.login.service.entity.packet.client.CAChallengeResponse;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.entity.packet.client.CAListWorld;
import com.aaemu.login.service.entity.packet.client.CAOtpNumber;
import com.aaemu.login.service.entity.packet.client.CARequestAuth;
import io.netty.channel.Channel;

public interface AuthService {

    void requestAuth(CARequestAuth requestAuth, Channel channel);

    void firstChallengeResponse(CAChallengeResponse challengeResponse, Channel channel);

    void secondChallengeResponse(CAChallengeResponse2 challengeResponse2, Channel channel);

    void otpProcess(CAOtpNumber caOtpNumber, Channel channel);

    void listWorldProcess(CAListWorld listWorld, Channel channel);
}
