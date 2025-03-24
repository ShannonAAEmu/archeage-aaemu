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
public enum Race {
    NUIAN(1, FractionMotherType.NUIAN_ALLIANCE),
    FAIRY(2, FractionMotherType.NUIAN_ALLIANCE),
    DWARF(3, FractionMotherType.NUIAN_ALLIANCE),
    ELF(4, FractionMotherType.NUIAN_ALLIANCE),
    HARIHARAN(5, FractionMotherType.HARANI_ALLIANCE),
    FERRE(6, FractionMotherType.HARANI_ALLIANCE),
    RETURNED(7, FractionMotherType.HARANI_ALLIANCE),
    WARBORN(8, FractionMotherType.HARANI_ALLIANCE);

    private final int race;
    private final FractionMotherType fractionMotherType;

    public static Race getById(int id) {
        for (Race race : values()) {
            if (race.getRace() == id) {
                return race;
            }
        }
        throw new GameServerException("Unknown char race id: " + id);
    }

    public static Race getById(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        return getById(byteBufUtil.readByte(byteBuf));
    }
}
