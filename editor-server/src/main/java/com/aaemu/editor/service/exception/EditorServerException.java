package com.aaemu.editor.service.exception;

public class EditorServerException extends RuntimeException {

    public EditorServerException(String message) {
        super(message);
    }

    public EditorServerException(Throwable cause) {
        super(cause);
    }
}
