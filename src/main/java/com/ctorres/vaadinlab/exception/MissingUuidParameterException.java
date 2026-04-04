package com.ctorres.vaadinlab.exception;

public class MissingUuidParameterException extends RuntimeException {
    public MissingUuidParameterException() {
        super("UUID parameter is required");
    }
}
