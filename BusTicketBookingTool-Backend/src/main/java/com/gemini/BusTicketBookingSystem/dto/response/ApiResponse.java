package com.gemini.BusTicketBookingSystem.dto.response;

/**
 * Generic API response wrapper used by all controller endpoints.
 * Wraps the actual response data with a status code and message
 * to provide a consistent response format across all APIs.
 *
 * @param <T> - the type of the actual response data (e.g., AgencyResponse, List<TripResponse>)
 *
 * Example JSON response:
 * {
 *   "statusCode": 200,
 *   "message": "Agency fetched successfully",
 *   "data": { ... }
 * }
 */
public class ApiResponse<T> {

    /** HTTP status code (e.g., 200 for OK, 201 for Created) */
    private int statusCode;

    /** Human-readable message describing the result (e.g., "Agency created successfully") */
    private String message;

    /** The actual response data payload */
    private T data;

    /** Default no-args constructor */
    public ApiResponse() {
    }

    /**
     * Creates an API response with all fields.
     *
     * @param statusCode - the HTTP status code
     * @param message    - the result message
     * @param data       - the response data
     */
    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    /** Gets the status code */
    public int getStatusCode() {
        return statusCode;
    }

    /** Gets the message */
    public String getMessage() {
        return message;
    }

    /** Gets the response data */
    public T getData() {
        return data;
    }

    /** Sets the status code */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /** Sets the message */
    public void setMessage(String message) {
        this.message = message;
    }

    /** Sets the response data */
    public void setData(T data) {
        this.data = data;
    }
}
