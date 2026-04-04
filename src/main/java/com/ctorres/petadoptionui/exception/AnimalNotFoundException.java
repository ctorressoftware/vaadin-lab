package com.ctorres.petadoptionui.exception;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String uuid) {
        super("Animal with UUID =" + uuid + " not found.");
    }
}
