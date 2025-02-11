package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Shannon
 */
@Data
@RequiredArgsConstructor
public class Face {
    private final ByteBufUtils byteBufUtil;
    private long hairColorId;
    private long skinColorId;
    private Imperfection imperfection;
    private Tattoo tattoo;
    private EyeLining eyeLining;
    private Brows brows;
    private Beard beard;
    private long diffuseMapId;
    private long skinId;   // normalMapId (decrepitude)
    private long eyelashMapId;
    private float normalMapWeight;
    private long lipColor;
    private long leftPupilColor;
    private long rightPupilColor;
    private long eyebrowColor;
    private long decoColor;
    private byte[] modifiers;

    public Face(ByteBufUtils byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.hairColorId = byteBufUtil.readD(byteBuf);
        this.skinColorId = byteBufUtil.readD(byteBuf);
        this.imperfection = new Imperfection(byteBufUtil, byteBuf);
        this.tattoo = new Tattoo(byteBufUtil, byteBuf);
        this.eyeLining = new EyeLining(byteBufUtil, byteBuf);
        this.brows = new Brows(byteBufUtil, byteBuf);
        this.beard = new Beard(byteBufUtil, byteBuf);
        this.diffuseMapId = byteBufUtil.readD(byteBuf);
        this.skinId = byteBufUtil.readD(byteBuf);
        this.eyelashMapId = byteBufUtil.readD(byteBuf);
        this.normalMapWeight = byteBufUtil.readF(byteBuf);
        this.lipColor = byteBufUtil.readD(byteBuf);
        this.leftPupilColor = byteBufUtil.readD(byteBuf);
        this.rightPupilColor = byteBufUtil.readD(byteBuf);
        this.eyebrowColor = byteBufUtil.readD(byteBuf);
        this.decoColor = byteBufUtil.readD(byteBuf);
        this.modifiers = new byte[byteBufUtil.readW(byteBuf)];
        for (int i = 0; i < this.modifiers.length; i++) {
            this.modifiers[i] = byteBufUtil.readB(byteBuf);
        }
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.writeD((int) hairColorId, byteBuf);
        byteBufUtil.writeD((int) skinColorId, byteBuf);
        byteBufUtil.write(imperfection.build(), byteBuf);
        byteBufUtil.write(tattoo.build(), byteBuf);
        byteBufUtil.write(eyeLining.build(), byteBuf);
        byteBufUtil.write(brows.build(), byteBuf);
        byteBufUtil.write(beard.build(), byteBuf);
        byteBufUtil.writeD((int) diffuseMapId, byteBuf);
        byteBufUtil.writeD((int) skinId, byteBuf);
        byteBufUtil.writeD((int) eyelashMapId, byteBuf);
        byteBufUtil.writeF(normalMapWeight, byteBuf);
        byteBufUtil.writeD((int) lipColor, byteBuf);
        byteBufUtil.writeD((int) leftPupilColor, byteBuf);
        byteBufUtil.writeD((int) rightPupilColor, byteBuf);
        byteBufUtil.writeD((int) eyebrowColor, byteBuf);
        byteBufUtil.writeD((int) decoColor, byteBuf);
        byteBufUtil.write(modifiers, byteBuf);
        return byteBuf;
    }
}
