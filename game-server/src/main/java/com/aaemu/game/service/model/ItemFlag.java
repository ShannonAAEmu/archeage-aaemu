package com.aaemu.game.service.model;

import com.aaemu.game.service.exception.GameServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemFlag {
    None((byte) 0),
    SoulBound((byte) 1),
    HasUCC((byte) 2),
    Secure((byte) 4),
    Skinized((byte) 8),
    Unpacked((byte) 10),
    AuctionWin((byte) 20);

    private final byte flag;

    public static ItemFlag getItemFlag(byte flag) {
        for (ItemFlag itemFlag : values()) {
            if (itemFlag.flag == flag) {
                return itemFlag;
            }
        }
        throw new GameServerException("Invalid flag: " + flag);
    }
}
