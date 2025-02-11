package com.aaemu.login.service.netty.decoder;

import io.netty.buffer.ByteBuf;

import java.nio.ByteOrder;

/**
 * @author Shannon
 */
public class LengthFieldBasedFrameDecoder extends io.netty.handler.codec.LengthFieldBasedFrameDecoder {

    public LengthFieldBasedFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        return buf.getUnsignedShortLE(offset);
    }
}
