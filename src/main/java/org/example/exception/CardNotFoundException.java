package org.example.exception;

public class CardNotFoundException extends Exception {
    public CardNotFoundException(String message) {
        super(message);
    }
}
