package com.gemini.BusTicketBookingSystem.exceptions;


/**
 * Custom exception thrown when trying to create a resource that already exists.
 * For example: registering a bus with a registration number that's already in use,
 * or creating a route that already exists between two cities.
 * Results in HTTP 409 (Conflict) response.
 */
public class DuplicateResourceException extends RuntimeException {

    /** The type of resource that has a duplicate (e.g., "Bus", "Route") */
    private String resourceName;

    /** The field that contains the duplicate value (e.g., "registrationNumber") */
    private String fieldName;

    /** The actual duplicate value (e.g., "TN01AB1234") */
    private Object fieldValue;

    /**
     * Creates a DuplicateResourceException with a formatted message.
     * Example: "Bus already exists with registrationNumber : 'TN01AB1234'"
     *
     * @param resourceName - the type of resource
     * @param fieldName    - the field with the duplicate
     * @param fieldValue   - the duplicate value
     */
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Creates a DuplicateResourceException with a custom message.
     *
     * @param message - the error message
     */
    public DuplicateResourceException(String message) {
        super(message);
    }

    /** Gets the resource name */
    public String getResourceName() {
        return resourceName;
    }

    /** Gets the field name */
    public String getFieldName() {
        return fieldName;
    }

    /** Gets the duplicate field value */
    public Object getFieldValue() {
        return fieldValue;
    }
}