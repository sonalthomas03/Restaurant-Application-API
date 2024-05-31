package com.Restaurant.application.exception;

import com.Restaurant.application.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<String> handleCustomAccessDeniedException(CustomAccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(tableEmptyException.class)
    public ResponseEntity<String> handleTableEmptyException(tableEmptyException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<String> ItemAlreadyExistsException(tableEmptyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(entityNotExistException.class)
    public ResponseEntity<String> entityNotExistException(entityNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }




    @ExceptionHandler(InvalidEntityDataException.class)
    public ResponseEntity<String> InvalidEntityDataException(entityNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(emailTakenException.class)
    public ResponseEntity<String> emailTakenException(emailTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
