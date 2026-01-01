package de.assecor.personen.exceptions;

public class BrokenResourceException extends RuntimeException {
    public BrokenResourceException(String message) {
        super(message);
    }
}

