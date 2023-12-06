package com.aaemu.login.handler;

import com.aaemu.login.service.entity.packet.ClientPacket;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse;
import com.aaemu.login.service.entity.packet.client.CAChallengeResponse2;
import com.aaemu.login.service.entity.packet.client.CAListWorld;
import com.aaemu.login.service.entity.packet.client.CAOtpNumber;
import com.aaemu.login.service.entity.packet.client.CARequestAuth;
import com.aaemu.login.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class Decoder extends MessageToMessageDecoder<ByteBuf> {
    private final ByteBufUtil byteBufUtil;

    public Decoder(ByteBufUtil byteBufUtil) {
        this.byteBufUtil = byteBufUtil;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        int length = byteBufUtil.readW(byteBuf);
        ClientPacket clientPacket = ClientPacket.getByOpcode(Integer.toHexString(byteBufUtil.readW(byteBuf)));
        log.info("Received packet: {} [opcode: {}, length: {}]", clientPacket.name(), clientPacket.getOpcode(), length);
        switch (clientPacket) {
            case CARequestAuth -> out.add(new CARequestAuth(byteBufUtil, byteBuf));
            case CAChallengeResponse -> out.add(new CAChallengeResponse(byteBufUtil, byteBuf));
            case CAChallengeResponse2 -> out.add(new CAChallengeResponse2(byteBufUtil, byteBuf));
            case CAOtpNumber -> out.add(new CAOtpNumber(byteBufUtil, byteBuf));
            case CAListWorld -> out.add(new CAListWorld(byteBufUtil, byteBuf));
        }
        if (byteBuf.readableBytes() != 0) {
            throw new RuntimeException(String.format("Not all bytes were read from the packet: %s [opcode: %s, size: %d]", clientPacket.name(), clientPacket.getOpcode(), byteBuf.readableBytes()));
        }
    }

}
