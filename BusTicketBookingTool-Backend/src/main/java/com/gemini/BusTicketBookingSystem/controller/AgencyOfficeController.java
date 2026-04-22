package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IAgencyOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing agency offices (branch offices of a travel agency).
 * Each agency can have multiple offices. This controller handles
 * creating, retrieving, updating, and deleting offices.
 * Base URL: /api/v1
 */
@RestController
@RequestMapping("/api/v1")
public class AgencyOfficeController {

    @Autowired
    private IAgencyOfficeService officeService;


    /**
     * Creates a new office for a specific agency.
     * Links the new office to the given agency using the agencyId from the URL path.
     * The office details (mail, contact person, contact number, address ID)
     * are provided in the request body.
     *
     * @param agencyId   - the ID of the agency this office belongs to
     * @param requestDTO - the office details to create (validated with @Valid)
     * @return ResponseEntity with HTTP 201 (Created) status and the saved office data
     */
    @PostMapping("/agencies/{agencyId}/offices")
    public ResponseEntity<ApiResponse<AgencyOfficeResponse>> createOffice(
            @PathVariable Integer agencyId,
            @Valid @RequestBody AgencyOfficeRequest requestDTO) {

        AgencyOfficeResponse response = officeService.addOffice(agencyId, requestDTO);

        ApiResponse<AgencyOfficeResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Office created successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    /**
     * Retrieves all offices that belong to a specific agency.
     * Uses the agencyId to filter offices for that particular agency.
     *
     * @param agencyId - the ID of the agency whose offices to retrieve
     * @return ResponseEntity with HTTP 200 (OK) status and a list of offices
     */
    @GetMapping("/agencies/{agencyId}/offices")
    public ResponseEntity<ApiResponse<List<AgencyOfficeResponse>>> getOfficesByAgency(
            @PathVariable Integer agencyId) {

        List<AgencyOfficeResponse> offices = officeService.getOfficesByAgency(agencyId);

        ApiResponse<List<AgencyOfficeResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Offices fetched successfully",
                        offices);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Retrieves a single office by its unique office ID.
     * If the office is not found, a ResourceNotFoundException is thrown.
     *
     * @param officeId - the unique identifier of the office to retrieve
     * @return ResponseEntity with HTTP 200 (OK) status and the office data
     */
    @GetMapping("/offices/{officeId}")
    public ResponseEntity<ApiResponse<AgencyOfficeResponse>> getOfficeById(
            @PathVariable Integer officeId) {

        AgencyOfficeResponse response = officeService.getOfficeById(officeId);

        ApiResponse<AgencyOfficeResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Office fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Updates an existing office's details.
     * Finds the office by its ID and updates the mail, contact person name,
     * contact number, and address with the values from the request body.
     *
     * @param officeId   - the unique ID of the office to update
     * @param requestDTO - the new office details (validated with @Valid)
     * @return ResponseEntity with HTTP 200 (OK) status and the updated office data
     */
    @PutMapping("/offices/{officeId}")
    public ResponseEntity<ApiResponse<AgencyOfficeResponse>> updateOffice(
            @PathVariable Integer officeId,
            @Valid @RequestBody AgencyOfficeRequest requestDTO) {

        AgencyOfficeResponse response = officeService.updateOffice(officeId, requestDTO);

        ApiResponse<AgencyOfficeResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Office updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    /**
     * Deactivates (deletes) an office by its ID.
     * Removes the office from the system. If the office is not found,
     * a ResourceNotFoundException is thrown.
     *
     * @param officeId - the unique ID of the office to deactivate
     * @return ResponseEntity with HTTP 200 (OK) status and a success message
     */
    @DeleteMapping("/offices/{officeId}")
    public ResponseEntity<ApiResponse<String>> deactivateOffice(
            @PathVariable Integer officeId) {

        officeService.deleteOffice(officeId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Office deactivated successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}