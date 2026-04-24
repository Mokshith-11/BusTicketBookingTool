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

@RestController
@RequestMapping("/api/v1")
/*
 * - This controller is the API entry point for Agency Office requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class AgencyOfficeController {

    @Autowired
    private IAgencyOfficeService officeService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
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
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
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


    // DELETE disables or removes a resource by ID.
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