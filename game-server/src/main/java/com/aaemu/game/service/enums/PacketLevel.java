package com.aaemu.game.service.enums;

import com.aaemu.game.service.exception.PacketException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum PacketLevel {
    _1("01", "0001"),
    _2("02", "0002");

    private final String level;
    private final String serverRawLevel;

    public static PacketLevel getByLevel(String rawLevel) {
        for (PacketLevel level : values()) {
            if (rawLevel.endsWith(level.getLevel())) {
                return level;
            }
        }
        throw new PacketException("Unknown client packet raw packet level: " + rawLevel);
    }
}
