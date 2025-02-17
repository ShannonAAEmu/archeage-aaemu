package com.aaemu.game.service;

import com.aaemu.game.service.dto.client.AccountFutureSet;
import com.aaemu.game.service.dto.packet.client.CSListCharacter;
import com.aaemu.game.service.dto.packet.client.X2EnterWorld;
import com.aaemu.game.service.dto.packet.proxy.FinishState;

/**
 * @author Shannon
 */
public interface AuthService {

    AccountFutureSet getAccountFutureSet();

    void enterWorld(X2EnterWorld packet);

    void finishState(FinishState packet);

    void changeState(int accountId);

    void sendCharacterList(CSListCharacter packet);
}
