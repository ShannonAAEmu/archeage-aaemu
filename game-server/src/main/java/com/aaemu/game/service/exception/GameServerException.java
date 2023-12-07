package com.aaemu.game.service.exception;

public class GameServerException extends RuntimeException {

    public GameServerException() {
        super();
    }

    public GameServerException(String message) {
        super(message);
    }

    public GameServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameServerException(Throwable cause) {
        super(cause);
    }
}
