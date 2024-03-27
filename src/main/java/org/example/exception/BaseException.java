package org.example.exception;

public class BaseException extends RuntimeException {
    private final String errorCode;

    protected BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
