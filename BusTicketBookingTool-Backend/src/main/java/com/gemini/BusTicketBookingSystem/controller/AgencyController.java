package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IAgencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing travel agencies.
 * Handles CRUD operations (Create, Read, Update, Delete) for agencies.
 * Base URL: /api/v1/agencies
 */
@RestController
@RequestMapping("/api/v1/agencies")
public class AgencyController {

    @Autowired
    private IAgencyService agencyService;


    /**
     * Creates a new travel agency.
     * Validates the request body and saves the agency details (name, contact person,
     * email, phone) to the database.
     *
     * @param requestDTO - the agency details to create (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) status and the saved agency data
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AgencyResponse>> createAgency(
            @Valid @RequestBody AgencyRequest requestDTO) {

        AgencyResponse response = agencyService.createAgency(requestDTO);

        ApiResponse<AgencyResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Agency created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    /**
     * Retrieves a list of all registered travel agencies.
     * Returns all agencies stored in the database.
     *
     * @return ResponseEntity with HTTP 200 (OK) status and a list of all agencies
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AgencyResponse>>> getAllAgencies() {

        List<AgencyResponse> agencies = agencyService.getAllAgencies();

        ApiResponse<List<AgencyResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Agencies fetched successfully",
                        agencies);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retrieves a single agency by its unique ID.
     * If no agency is found with the given ID, a ResourceNotFoundException is thrown.
     *
     * @param agencyId - the unique identifier of the agency to retrieve
     * @return ResponseEntity with HTTP 200 (OK) status and the agency data
     */
    @GetMapping("/{agencyId}")
    public ResponseEntity<ApiResponse<AgencyResponse>> getAgencyById(
            @PathVariable Integer agencyId) {

        AgencyResponse response = agencyService.getAgencyById(agencyId);

        ApiResponse<AgencyResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Agency fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Updates an existing agency's details.
     * Finds the agency by ID and updates its fields (name, contact person, email, phone)
     * with the values provided in the request body. Validates input before updating.
     *
     * @param agencyId   - the unique ID of the agency to update
     * @param requestDTO - the new agency details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) status and the updated agency data
     */
    @PutMapping("/{agencyId}")
    public ResponseEntity<ApiResponse<AgencyResponse>> updateAgency(
            @PathVariable Integer agencyId,
            @Valid @RequestBody AgencyRequest requestDTO) {

        AgencyResponse response = agencyService.updateAgency(agencyId, requestDTO);

        ApiResponse<AgencyResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Agency updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Deactivates (soft deletes) an agency by its ID.
     * Removes the agency from the system. If the agency is not found,
     * a ResourceNotFoundException is thrown.
     *
     * @param agencyId - the unique ID of the agency to deactivate
     * @return ResponseEntity with HTTP 200 (OK) status and a success message
     */
    @DeleteMapping("/{agencyId}")
    public ResponseEntity<ApiResponse<String>> deactivateAgency(
            @PathVariable Integer agencyId) {

        agencyService.deleteAgency(agencyId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Agency deactivated successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }

}