package com.aaemu.game.service.enums.unit;

import com.aaemu.game.service.exception.GameServerException;
import com.aaemu.game.service.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@RequiredArgsConstructor
@Getter
public enum Gender {
    MALE(1),
    FEMALE(2);

    private final int gender;

    public static Gender getById(int id) {
        for (Gender gender : values()) {
            if (gender.getGender() == id) {
                return gender;
            }
        }
        throw new GameServerException("Unknown char gender id: " + id);
    }

    public static Gender getById(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        return getById(byteBufUtil.readByte(byteBuf));
    }
}
