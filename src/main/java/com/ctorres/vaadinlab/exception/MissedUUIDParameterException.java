package com.ctorres.vaadinlab.exception;

public class MissedUUIDParameterException extends RuntimeException {
    public MissedUUIDParameterException() {
        super("UUID parameter is required");
    }
}
