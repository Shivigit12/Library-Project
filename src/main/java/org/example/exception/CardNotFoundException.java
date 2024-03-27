package org.example.exception;

public class CardNotFoundException extends BaseException {
    public CardNotFoundException(String message) {

        super("CARD_NOT_FOUND", message);
    }
}
