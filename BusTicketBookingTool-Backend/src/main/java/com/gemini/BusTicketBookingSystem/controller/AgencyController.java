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

@RestController
@RequestMapping("/api/v1/agencies")
/*
 * - This controller is the API entry point for Agency requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class AgencyController {

    @Autowired
    private IAgencyService agencyService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
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
    // DELETE disables or removes a resource by ID.
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