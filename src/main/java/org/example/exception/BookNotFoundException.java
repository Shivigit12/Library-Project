package org.example.exception;

public class BookNotFoundException extends BaseException {
    public BookNotFoundException(String message) {
        super("NO_BOOK_FOUND", message);
    }
}
