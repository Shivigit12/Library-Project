package org.example.controller;

import org.example.exception.CardNotFoundException;
import org.example.exception.TransactionException;
import org.example.utility.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @Autowired
    Environment environment;
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorInfo> transactionExceptionHandler(TransactionException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Book is not found");
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorInfo> cardExceptionHandler(CardNotFoundException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Card is not active");
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
