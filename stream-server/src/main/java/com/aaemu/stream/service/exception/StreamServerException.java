package com.aaemu.stream.service.exception;

public class StreamServerException extends RuntimeException {

    public StreamServerException(String message) {
        super(message);
    }

    public StreamServerException(Throwable cause) {
        super(cause);
    }
}
