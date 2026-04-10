package com.example.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @ControllerAdvice is a powerful interception mechanism. 
 * Any time an exception is thrown in ANY of our Controllers, it gets routed here first!
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handling Invalid Form Data (e.g., blank name, bad email)
    // When @Valid fails in a controller, it throws a MethodArgumentNotValidException.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Loop through all the validation errors and extract the field name + the message
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        // Return the clean map of errors (like {"email": "must be a well-formed email address"}) with a 400 Bad Request
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
