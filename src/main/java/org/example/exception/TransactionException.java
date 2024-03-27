package org.example.exception;

public class TransactionException extends BaseException{
    public TransactionException(String message) {

        super("TRANSACTION_ERROR", message);
    }
}
