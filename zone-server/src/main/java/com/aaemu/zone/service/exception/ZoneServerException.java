package com.aaemu.zone.service.exception;

public class ZoneServerException extends RuntimeException {

    public ZoneServerException() {
        super();
    }

    public ZoneServerException(String message) {
        super(message);
    }

    public ZoneServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZoneServerException(Throwable cause) {
        super(cause);
    }
}
