package com.example.sms.exception;

// Import HttpStatus to assign HTTP response codes to exceptions.
import org.springframework.http.HttpStatus;
// Import ResponseEntity so we can craft a custom HTTP response containing our error map.
import org.springframework.http.ResponseEntity;
// Import FieldError to inspect exactly which validation rule failed.
import org.springframework.validation.FieldError;
// This exception is thrown specifically when a @Valid annotation fails.
import org.springframework.web.bind.MethodArgumentNotValidException;
// Import @ControllerAdvice to intercept exceptions globally across the app.
import org.springframework.web.bind.annotation.ControllerAdvice;
// Import @ExceptionHandler to define which specific exception a method handles.
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @ControllerAdvice is a powerful interception mechanism. 
 * Any time an exception is thrown in ANY of our Controllers, it gets routed here first!
 * It acts like a giant "try-catch" block surrounding the entire application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handling Invalid Form Data (e.g., blank name, bad email)
    // The @ExceptionHandler annotation tells Spring: "If a MethodArgumentNotValidException is thrown anywhere, run this method!"
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        // We initialize an empty HashMap to hold clean, readable error messages.
        // It will look like this in JSON: {"email": "must be a well-formed email address", "name": "Name is mandatory"}
        Map<String, String> errors = new HashMap<>();
        
        // ex.getBindingResult().getAllErrors() gives us a raw list of all the @NotBlank or @Email rules that failed.
        // We use a forEach loop to iterate through every single failed rule.
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // We cast the error to a FieldError so we can extract the exact name of the broken variable (like "email").
            String fieldName = ((FieldError) error).getField();
            // We extract the human-readable default message we set up earlier (like "Email is mandatory").
            String errorMessage = error.getDefaultMessage();
            // We put the field name and error message into our HashMap.
            errors.put(fieldName, errorMessage);
        });
        
        // We return the HashMap directly back to the user/frontend, along with a 400 BAC_REQUEST status.
        // This stops the app from crashing and gives the user instructions on how to fix their submission!
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
