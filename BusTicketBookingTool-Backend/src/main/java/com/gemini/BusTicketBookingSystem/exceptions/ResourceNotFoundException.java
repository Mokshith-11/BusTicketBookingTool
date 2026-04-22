package com.gemini.BusTicketBookingSystem.exceptions;

/**
 * Custom exception thrown when a requested resource is not found in the database.
 * For example: looking up a trip, customer, or booking by ID that doesn't exist.
 * Results in HTTP 404 (Not Found) response.
 */
public class ResourceNotFoundException extends RuntimeException {

    /** The type of resource that was not found (e.g., "Trip", "Customer") */
    private String resourceName;

    /** The field used to look up the resource (e.g., "tripId", "customerId") */
    private String fieldName;

    /** The value that was searched for (e.g., 42) */
    private Object fieldValue;

    /**
     * Creates a ResourceNotFoundException with a formatted message.
     * Example: "Trip not found with tripId : '42'"
     *
     * @param resourceName - the type of resource
     * @param fieldName    - the lookup field
     * @param fieldValue   - the searched value
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * Creates a ResourceNotFoundException with a custom message.
     *
     * @param message - the error message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates a ResourceNotFoundException with resource name and integer ID.
     * Convenience constructor for simple lookups.
     *
     * @param agency   - the resource type name
     * @param agencyId - the integer ID that was not found
     */
    public ResourceNotFoundException(String agency, Integer agencyId) {
    }

    /** Gets the resource name */
    public String getResourceName() {
        return resourceName;
    }

    /** Gets the field name */
    public String getFieldName() {
        return fieldName;
    }

    /** Gets the searched field value */
    public Object getFieldValue() {
        return fieldValue;
    }
}