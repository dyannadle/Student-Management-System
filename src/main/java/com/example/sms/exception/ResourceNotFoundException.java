package com.example.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The @ResponseStatus annotation automatically tells Spring Boot: 
 * "If this exception is ever thrown, immediately return an HTTP 404 NOT FOUND status to the user!"
 * Instead of returning a 500 Server Crash, it gracefully informs the user that data is missing.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
// It "extends RuntimeException" because it is an unchecked exception (it happens while the app is running).
public class ResourceNotFoundException extends RuntimeException{

    // What kind of thing were we looking for? (e.g. "Student")
    private String resourceName;
    // What specific property were we searching by? (e.g. "Id" or "Email")
    private String fieldName;
    // What was the actual value that we failed to find? (e.g. "5" or "test@test.com")
    private Object fieldValue;

    // This constructor builds the exception using the three variables above.
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        
        // "super" calls the constructor of the parent class (RuntimeException), 
        // allowing us to set the main error message using String.format.
        // This produces a beautiful message like: "Student not found with Id : '5'".
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        
        // We assign the passed-in values to our class variables so they can be accessed later if needed.
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Standard getter to retrieve the resource name. No annotations needed.
    public String getResourceName() { return resourceName; }
    // Standard getter to retrieve the field name. No annotations needed.
    public String getFieldName() { return fieldName; }
    // Standard getter to retrieve the field value. No annotations needed.
    public Object getFieldValue() { return fieldValue; }
}
