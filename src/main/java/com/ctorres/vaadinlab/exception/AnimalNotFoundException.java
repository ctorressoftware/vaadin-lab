package com.ctorres.vaadinlab.exception;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String uuid) {
        super("Animal with UUID =" + uuid + " not found.");
    }
}
