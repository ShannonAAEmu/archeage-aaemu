package com.aaemu.stream.service.exception;

public class StreamServerException extends RuntimeException {

    public StreamServerException() {
        super();
    }

    public StreamServerException(String message) {
        super(message);
    }

    public StreamServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamServerException(Throwable cause) {
        super(cause);
    }
}
