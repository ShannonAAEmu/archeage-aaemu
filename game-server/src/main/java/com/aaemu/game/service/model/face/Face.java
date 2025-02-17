package com.aaemu.game.service.model.face;

import com.aaemu.game.service.util.ByteBufUtil;
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
    private final ByteBufUtil byteBufUtil;
    private Imperfection imperfection;
    private Tattoo tattoo;
    private EyeLining eyeLining;
    private Brows brows;
    private Beard beard;
    private int diffuseMapId;   // type
    private int skinId;   // normalMapId (decrepitude) type
    private int eyelashMapId;   // type
    private float normalMapWeight;  // weight
    private int lipColor;       // lip
    private int leftPupilColor; // leftPupil
    private int rightPupilColor;    // rightPupil
    private int eyebrowColor;   // eyebrow
    private int decoColor;      // deco
    private byte[] modifiers;

    public Face(ByteBufUtil byteBufUtil, ByteBuf byteBuf) {
        this.byteBufUtil = byteBufUtil;
        this.imperfection = new Imperfection(byteBufUtil, byteBuf);
        this.tattoo = new Tattoo(byteBufUtil, byteBuf);
        this.eyeLining = new EyeLining(byteBufUtil, byteBuf);
        this.brows = new Brows(byteBufUtil, byteBuf);
        this.beard = new Beard(byteBufUtil, byteBuf);
        this.diffuseMapId = byteBufUtil.readInt(byteBuf);
        this.skinId = byteBufUtil.readInt(byteBuf);
        this.eyelashMapId = byteBufUtil.readInt(byteBuf);
        this.normalMapWeight = byteBufUtil.readFloat(byteBuf);
        this.lipColor = byteBufUtil.readInt(byteBuf);
        this.leftPupilColor = byteBufUtil.readInt(byteBuf);
        this.rightPupilColor = byteBufUtil.readInt(byteBuf);
        this.eyebrowColor = byteBufUtil.readInt(byteBuf);
        this.decoColor = byteBufUtil.readInt(byteBuf);
        this.modifiers = new byte[byteBufUtil.readShort(byteBuf)];
        for (int i = 0; i < this.modifiers.length; i++) {
            this.modifiers[i] = byteBufUtil.readByte(byteBuf);
        }
    }

    public ByteBuf build() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBufUtil.write(imperfection.build(), byteBuf);
        byteBufUtil.write(tattoo.build(), byteBuf);
        byteBufUtil.write(eyeLining.build(), byteBuf);
        byteBufUtil.write(brows.build(), byteBuf);
        byteBufUtil.write(beard.build(), byteBuf);
        byteBufUtil.writeInt(diffuseMapId, byteBuf);
        byteBufUtil.writeInt(skinId, byteBuf);
        byteBufUtil.writeInt(eyelashMapId, byteBuf);
        byteBufUtil.writeFloat(normalMapWeight, byteBuf);
        byteBufUtil.writeInt(lipColor, byteBuf);
        byteBufUtil.writeInt(leftPupilColor, byteBuf);
        byteBufUtil.writeInt(rightPupilColor, byteBuf);
        byteBufUtil.writeInt(eyebrowColor, byteBuf);
        byteBufUtil.writeInt(decoColor, byteBuf);
        byteBufUtil.writeShort(modifiers.length, byteBuf);
        byteBufUtil.write(modifiers, byteBuf);
        return byteBuf;
    }
}
