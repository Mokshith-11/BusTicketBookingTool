package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IDriverService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing bus drivers.
 * Handles registering drivers under an office, viewing driver details,
 * updating driver information, and removing drivers from the system.
 * Base URL: /api/v1
 */
@RestController
@RequestMapping("/api/v1")
public class DriverController {

    @Autowired
    private IDriverService driverService;


    /**
     * Registers a new driver under a specific agency office.
     * Links the driver to the office using the officeId from the URL.
     * Checks for duplicate license numbers before saving.
     * Driver details (license number, name, phone, addressId) come from the request body.
     *
     * @param officeId   - the ID of the office this driver is assigned to
     * @param requestDTO - driver details to register (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) status and the registered driver data
     */
    @PostMapping("/offices/{officeId}/drivers")
    public ResponseEntity<ApiResponse<DriverResponse>> registerDriver(
            @PathVariable Integer officeId,
            @Valid @RequestBody DriverRequest requestDTO) {

        DriverResponse response = driverService.registerDriver(officeId, requestDTO);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Driver registered successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    /**
     * Retrieves all drivers that belong to a specific agency office.
     * Uses the officeId to filter and return only drivers assigned to that office.
     *
     * @param officeId - the ID of the office whose drivers to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and a list of drivers for that office
     */
    @GetMapping("/offices/{officeId}/drivers")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getDriversByOffice(
            @PathVariable Integer officeId) {

        List<DriverResponse> drivers = driverService.getDriversByOffice(officeId);

        ApiResponse<List<DriverResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Drivers fetched successfully",
                        drivers);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retrieves a single driver by their unique driver ID.
     * If the driver is not found, a ResourceNotFoundException is thrown.
     *
     * @param driverId - the unique identifier of the driver to retrieve
     * @return ResponseEntity with HTTP 200 (OK) and the driver details
     */
    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(
            @PathVariable Integer driverId) {

        DriverResponse response = driverService.getDriverById(driverId);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Updates an existing driver's details.
     * Finds the driver by ID and updates their license number, name, phone, and address.
     * Checks for duplicate license numbers if the license is being changed.
     *
     * @param driverId   - the unique ID of the driver to update
     * @param requestDTO - the new driver details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) and the updated driver data
     */
    @PutMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponse>> updateDriver(
            @PathVariable Integer driverId,
            @Valid @RequestBody DriverRequest requestDTO) {

        DriverResponse response = driverService.updateDriver(driverId, requestDTO);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Removes a driver from the system by their ID.
     * Permanently deletes the driver record. If the driver is not found,
     * a ResourceNotFoundException is thrown.
     *
     * @param driverId - the unique ID of the driver to remove
     * @return ResponseEntity with HTTP 200 (OK) and a success message
     */
    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<String>> removeDriver(
            @PathVariable Integer driverId) {

        driverService.removeDriver(driverId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver removed successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}
