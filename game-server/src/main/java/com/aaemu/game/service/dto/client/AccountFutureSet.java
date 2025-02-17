package com.aaemu.game.service.dto.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class AccountFutureSet {
    private final byte totalCharactersLimit;
    private final boolean isPreSelectCharacterPeriod;
    private final byte[] unknown;

    public AccountFutureSet(byte totalCharactersLimit, boolean isPreSelectCharacterPeriod) {
        this.totalCharactersLimit = totalCharactersLimit;
        this.isPreSelectCharacterPeriod = isPreSelectCharacterPeriod;
        this.unknown = new byte[]{0, 0, 0, 0, 0, 0};
    }
}
