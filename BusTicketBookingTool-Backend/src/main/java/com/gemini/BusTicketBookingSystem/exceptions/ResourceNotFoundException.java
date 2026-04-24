package com.gemini.BusTicketBookingSystem.exceptions;
/*
 * Beginner guide:
 * - This custom exception gives the service layer a readable way to report a specific Resource Not Found Exception problem.
 * - Instead of returning random errors, services throw this exception when a known business rule fails.
 * - GlobalExceptionHandler catches it and converts it into a proper JSON error response for the frontend.
 */
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String agency, Integer agencyId) {
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