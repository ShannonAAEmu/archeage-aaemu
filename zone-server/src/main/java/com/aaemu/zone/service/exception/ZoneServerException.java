package com.aaemu.zone.service.exception;

public class ZoneServerException extends RuntimeException {

    public ZoneServerException(String message) {
        super(message);
    }

    public ZoneServerException(Throwable cause) {
        super(cause);
    }
}
