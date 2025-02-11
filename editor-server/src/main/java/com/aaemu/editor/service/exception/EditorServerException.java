package com.aaemu.editor.service.exception;

public class EditorServerException extends RuntimeException {

    public EditorServerException() {
        super();
    }

    public EditorServerException(String message) {
        super(message);
    }

    public EditorServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditorServerException(Throwable cause) {
        super(cause);
    }
}
