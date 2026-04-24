package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.PaymentRequest;
import com.gemini.BusTicketBookingSystem.dto.response.PaymentResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.enums.PaymentStatus;
import com.gemini.BusTicketBookingSystem.service.IPaymentService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
/*
 * Beginner guide:
 * - This controller is the API entry point for Payment requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    // ✅ MAKE PAYMENT
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> makePayment(
            @Valid @RequestBody PaymentRequest requestDTO) {

        PaymentResponse response = paymentService.makePayment(requestDTO);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Payment successful",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // ✅ GET PAYMENT BY ID
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(
            @PathVariable Integer paymentId) {

        PaymentResponse response = paymentService.getPaymentById(paymentId);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ GET CUSTOMER PAYMENTS
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
    @GetMapping("/customers/{customerId}/payments")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getCustomerPayments(
            @PathVariable Integer customerId) {

        List<PaymentResponse> payments = paymentService.getCustomerPayments(customerId);

        ApiResponse<List<PaymentResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Customer payments fetched successfully",
                        payments);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ GET BOOKING PAYMENT
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
    @GetMapping("/bookings/{bookingId}/payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> getBookingPayment(
            @PathVariable Integer bookingId) {

        PaymentResponse response = paymentService.getBookingPayment(bookingId);

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Booking payment fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    // ✅ UPDATE PAYMENT STATUS
    /*
     * PATCH flow:
     * - URL points to the existing record and the request changes only one small part, such as status or close action.
     * - Service checks whether the operation is allowed before saving.
     * - This is used when a full update is not needed.
     */
    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @RequestParam String status) {

        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, PaymentStatus.fromValue(status));

        ApiResponse<PaymentResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment status updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }
}
