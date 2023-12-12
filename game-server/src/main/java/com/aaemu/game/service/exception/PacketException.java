package com.aaemu.game.service.exception;

public class PacketException extends GameServerException {

    public PacketException() {
    }

    public PacketException(String message) {
        super(message);
    }

    public PacketException(String message, Throwable cause) {
        super(message, cause);
    }

    public PacketException(Throwable cause) {
        super(cause);
    }
}
