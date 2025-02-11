package com.aaemu.game.service.exception;

public class GameServerException extends RuntimeException {

    public GameServerException(String message) {
        super(message);
    }

    public GameServerException(Throwable cause) {
        super(cause);
    }
}
