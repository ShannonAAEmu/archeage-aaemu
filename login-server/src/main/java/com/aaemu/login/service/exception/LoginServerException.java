package com.aaemu.login.service.exception;

/**
 * @author Shannon
 */
public class LoginServerException extends RuntimeException {

    public LoginServerException(String message) {
        super(message);
    }

    public LoginServerException(Throwable cause) {
        super(cause);
    }
}
