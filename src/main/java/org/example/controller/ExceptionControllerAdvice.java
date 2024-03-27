package org.example.controller;

import org.example.exception.BaseException;
import org.example.exception.CardNotFoundException;
import org.example.exception.TransactionException;
import org.example.utility.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @Autowired
    Environment environment;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fe : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }

        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Validation failed");
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorKey("VALIDATION_ERROR");
        error.setFieldErrors(fieldErrors);
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> httpMessageNotReadableHandler(HttpMessageNotReadableException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Malformed JSON request");
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorKey("MALFORMED_JSON");
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorInfo> baseExceptionHandler(BaseException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setErrorKey(exception.getErrorCode());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setErrorKey("INTERNAL_ERROR");
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorInfo> transactionExceptionHandler(TransactionException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Book is not found");
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setErrorKey(exception.getErrorCode());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorInfo> cardExceptionHandler(CardNotFoundException exception) {
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Card is not active");
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setErrorKey(exception.getErrorCode());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
