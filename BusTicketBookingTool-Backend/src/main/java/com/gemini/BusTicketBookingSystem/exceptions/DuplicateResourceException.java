package com.gemini.BusTicketBookingSystem.exceptions;
/*
 * - This custom exception gives the service layer a readable way to report a specific Duplicate Resource Exception problem.
 * - Instead of returning random errors, services throw this exception when a known business rule fails.
 * - GlobalExceptionHandler catches it and converts it into a proper JSON error response for the frontend.
 */


public class DuplicateResourceException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public DuplicateResourceException(String message) {
        super(message);
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}