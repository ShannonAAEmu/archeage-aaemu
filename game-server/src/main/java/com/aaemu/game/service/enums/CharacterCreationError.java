package com.aaemu.game.service.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum CharacterCreationError {
    OK(0),
    FAILED(3),  // Also generates Name is pending deletion
    NAME_ALREADY_EXISTS(4),
    INVALID_CHARACTERS(5);

    private final int reason;
}
