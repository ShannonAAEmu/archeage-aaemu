package com.aaemu.game.service.util;

import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.server.SCCharacterCreationFailedPacket;
import com.aaemu.game.service.enums.CharacterCreationError;
import com.aaemu.game.service.enums.unit.UnitAbilityType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Shannon
 */
@Component
@RequiredArgsConstructor
public class ValidatorUtils {
    private final ByteBufUtils byteBufUtils;

    public boolean isValidForCreate(CSCreateCharacter packet) {
        if (!isValidCharName(packet.getName())) {
            SCCharacterCreationFailedPacket characterCreationFailedPacket = new SCCharacterCreationFailedPacket();
            characterCreationFailedPacket.setReason(CharacterCreationError.INVALID_CHARACTERS);
            packet.getChannel().writeAndFlush(characterCreationFailedPacket.build(byteBufUtils));
            return false;
        }
        if (!UnitAbilityType.NONE.equals(packet.getUnitAbilityList().get(1)) && !UnitAbilityType.NONE.equals(packet.getUnitAbilityList().get(2))) {
            // TODO Send error msg
            return false;
        }
        if (1 != packet.getLevel()) {
            // TODO Send error msg
            return false;
        }
        return true;
    }

    public boolean isValidCharName(String name) {
        return StringUtils.isAlpha(name);
    }
}
