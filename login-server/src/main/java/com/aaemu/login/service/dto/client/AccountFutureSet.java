package com.aaemu.login.service.dto.client;

import lombok.Data;

/**
 * afs[0] -> X2World:GetTotalCharactersLimit() <br>
 * afs[1] -> X2World:IsPreSelectCharacterPeriod() <br>
 * afs[2] -> ? <br>
 * afs[3] -> ? <br>
 * afs[4] -> ? <br>
 * afs[5] -> ? <br>
 * afs[6] -> ? <br>
 * afs[7] -> ? <br>
 * afs[8] -> ? <br>
 *
 * @author Shannon
 */
@Data
public class AccountFutureSet {
    private byte totalCharactersLimit;
    private boolean isPreSelectCharacterPeriod;
    private byte[] unknown;
}
