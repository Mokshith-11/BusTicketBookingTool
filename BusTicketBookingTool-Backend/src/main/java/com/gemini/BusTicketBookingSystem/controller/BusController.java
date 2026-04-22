package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IBusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing buses.
 * Handles registering new buses under an office, viewing bus details,
 * updating bus information, and retiring (deleting) buses.
 * Base URL: /api/v1
 */
@RestController
@RequestMapping("/api/v1")
public class BusController {

    @Autowired
    private IBusService busService;


    /**
     * Registers a new bus under a specific agency office.
     * Links the bus to the office using the officeId from the URL.
     * Checks for duplicate registration numbers before saving.
     * The bus details (registration number, capacity, type) come from the request body.
     *
     * @param officeId   - the ID of the office this bus belongs to
     * @param requestDTO - bus details to register (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) status and the registered bus data
     */
    @PostMapping("/offices/{officeId}/buses")
    public ResponseEntity<ApiResponse<BusResponse>> registerBus(
            @PathVariable Integer officeId,
            @Valid @RequestBody BusRequest requestDTO) {

        BusResponse response = busService.registerBus(officeId, requestDTO);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Bus registered successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    /**
     * Retrieves all buses that belong to a specific agency office.
     * Uses the officeId to filter and return only buses assigned to that office.
     *
     * @param officeId - the ID of the office whose buses to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of buses for that office
     */
    @GetMapping("/offices/{officeId}/buses")
    public ResponseEntity<ApiResponse<List<BusResponse>>> getBusesByOffice(
            @PathVariable Integer officeId) {

        List<BusResponse> buses = busService.getBusesByOffice(officeId);

        ApiResponse<List<BusResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Buses fetched successfully",
                        buses);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retrieves a single bus by its unique bus ID.
     * If the bus is not found, a ResourceNotFoundException is thrown.
     *
     * @param busId - the unique identifier of the bus to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the bus details
     */
    @GetMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<BusResponse>> getBusById(
            @PathVariable Integer busId) {

        BusResponse response = busService.getBusById(busId);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Updates an existing bus's details.
     * Finds the bus by ID and updates its registration number, capacity, and type.
     * Checks for duplicate registration numbers if the number is being changed.
     *
     * @param busId      - the unique ID of the bus to update
     * @param requestDTO - the new bus details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) and the updated bus data
     */
    @PutMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<BusResponse>> updateBus(
            @PathVariable Integer busId,
            @Valid @RequestBody BusRequest requestDTO) {

        BusResponse response = busService.updateBus(busId, requestDTO);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retires (deletes) a bus from the system by its ID.
     * Permanently removes the bus record. If the bus is not found,
     * a ResourceNotFoundException is thrown.
     *
     * @param busId - the unique ID of the bus to retire
     * @return ResponseEntity with HTTP 200 (OK) and a success message
     */
    @DeleteMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<String>> retireBus(
            @PathVariable Integer busId) {

        busService.retireBus(busId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus retired successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}