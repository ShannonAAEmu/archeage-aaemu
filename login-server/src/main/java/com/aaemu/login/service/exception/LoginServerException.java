package com.aaemu.login.service.exception;

public class LoginServerException extends RuntimeException {

    public LoginServerException() {
        super();
    }

    public LoginServerException(String message) {
        super(message);
    }

    public LoginServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginServerException(Throwable cause) {
        super(cause);
    }
}
