package com.aaemu.game.service.enums.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum CharacterCreationError {
    OK((byte) 0),
    FAILED((byte) 3),  // Also generates Name is pending deletion
    NAME_ALREADY_EXISTS((byte) 4),
    INVALID_CHARACTERS((byte) 5);

    private final byte reason;
}
