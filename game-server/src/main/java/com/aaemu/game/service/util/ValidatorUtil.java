package com.aaemu.game.service.util;

import com.aaemu.game.service.dto.packet.client.CSCreateCharacter;
import com.aaemu.game.service.dto.packet.server.SCCharacterCreationFailed;
import com.aaemu.game.service.enums.packet.CharacterCreationError;
import com.aaemu.game.service.enums.unit.Ability;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Shannon
 */
@Component
@RequiredArgsConstructor
public class ValidatorUtil {
    private final ByteBufUtil byteBufUtil;

    public boolean isValidForCreate(CSCreateCharacter packet) {
        if (!isValidCharName(packet.getName())) {
            SCCharacterCreationFailed characterCreationFailedPacket = new SCCharacterCreationFailed();
            characterCreationFailedPacket.setReason(CharacterCreationError.INVALID_CHARACTERS);
            packet.getChannel().writeAndFlush(characterCreationFailedPacket.build(byteBufUtil));
            return false;
        }
//        if (!Ability.NONE.equals(packet.getAbility_1())) {
//            // TODO Send error msg
//            return false;
//        }
//        if (1 != packet.getLevel()) {
//            // TODO Send error msg
//            return false;
//        }
        return true;
    }

    public boolean isValidCharName(String name) {
        return StringUtils.isAlpha(name);
    }
}
