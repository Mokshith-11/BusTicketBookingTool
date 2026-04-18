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
public class AgencyOfficeController {

    @Autowired
    private IAgencyOfficeService officeService;


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