package com.gemini.BusTicketBookingSystem.exceptions;

/**
 * Custom exception thrown when attempting to create a resource that already exists.
 * This can happen when:
 * - Creating an agency with duplicate email
 * - Registering a bus with duplicate registration number
 * - Creating a driver with duplicate license number
 * - Registering a customer with duplicate email
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