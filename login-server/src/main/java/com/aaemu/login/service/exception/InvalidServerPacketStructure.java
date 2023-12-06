package com.aaemu.login.service.exception;

public class InvalidServerPacketStructure extends LoginServerException {

    public InvalidServerPacketStructure() {
    }

    public InvalidServerPacketStructure(String message) {
        super(message);
    }

    public InvalidServerPacketStructure(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidServerPacketStructure(Throwable cause) {
        super(cause);
    }

}
