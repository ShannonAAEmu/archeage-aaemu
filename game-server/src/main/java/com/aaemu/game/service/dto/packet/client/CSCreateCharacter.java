package com.aaemu.game.service.dto.packet.client;

import com.aaemu.game.service.dto.packet.ClientPacket;
import com.aaemu.game.service.model.SomeTypeWeight;
import com.aaemu.game.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CSCreateCharacter implements ClientPacket {
    private final String name;
    private final int charRace;       // CharRace
    private final int charGender;     // CharGender
    private final List<Long> typeList;
    private final int ext;
    private final List<Integer> abilityList;  // a
    private final int level;
    private long type_1;
    private long type_2;
    private long type_3;
    private float weight_1;
    private float scale;
    private float rotate;
    private int moveX;
    private int moveY;
    private List<SomeTypeWeight> someTypeWeightList;
    private long type_4;
    private long type_5;
    private long type_6;
    private float weight_2;
    private long lip;
    private long leftPupil;
    private long rightPupil;
    private long eyeBrow;       // eyebrow
    private long deco;
    private String modifiers;

    public CSCreateCharacter(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.name = byteBufUtil.readS(byteBuf);
        this.charRace = byteBufUtil.readB(byteBuf);
        this.charGender = byteBufUtil.readB(byteBuf);
        this.typeList = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            this.typeList.add(byteBufUtil.readD(byteBuf));
        }
        this.ext = byteBufUtil.readB(byteBuf);
        readTypes(byteBufUtil, byteBuf);
        this.abilityList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            this.abilityList.add(byteBufUtil.readB(byteBuf));
        }
        this.level = byteBufUtil.readB(byteBuf);
    }

    private void readTypes(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        if (this.ext == 1) {
            this.type_1 = byteBufUtil.readD(byteBuf);
        } else if (this.ext == 2) {
            this.type_1 = byteBufUtil.readD(byteBuf);
            this.type_2 = byteBufUtil.readD(byteBuf);
        } else if (this.ext == 3) {
            this.type_1 = byteBufUtil.readD(byteBuf);
            this.type_2 = byteBufUtil.readD(byteBuf);
            this.type_3 = byteBufUtil.readD(byteBuf);
            this.weight_1 = byteBufUtil.readFloat(byteBuf);
            this.scale = byteBufUtil.readFloat(byteBuf);
            this.rotate = byteBufUtil.readFloat(byteBuf);
            this.moveX = byteBufUtil.readW(byteBuf);
            this.moveY = byteBufUtil.readW(byteBuf);
            this.someTypeWeightList = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                SomeTypeWeight someTypeWeight = new SomeTypeWeight();
                someTypeWeight.setType(byteBufUtil.readD(byteBuf));
                someTypeWeight.setWeight(byteBufUtil.readFloat(byteBuf));
            }
            this.type_4 = byteBufUtil.readD(byteBuf);
            this.type_5 = byteBufUtil.readD(byteBuf);
            this.type_6 = byteBufUtil.readD(byteBuf);
            this.weight_2 = byteBufUtil.readFloat(byteBuf);
            this.lip = byteBufUtil.readD(byteBuf);
            this.leftPupil = byteBufUtil.readD(byteBuf);
            this.rightPupil = byteBufUtil.readD(byteBuf);
            this.eyeBrow = byteBufUtil.readD(byteBuf);
            this.deco = byteBufUtil.readD(byteBuf);
            this.modifiers = byteBufUtil.readS(byteBuf);
        }
    }
}
