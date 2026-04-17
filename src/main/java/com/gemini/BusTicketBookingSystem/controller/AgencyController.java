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
public class AgencyController {

    @Autowired
    private IAgencyService agencyService;


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


    @GetMapping
    public ResponseEntity<ApiResponse<List<AgencyResponse>>> getAllAgencies() {

        List<AgencyResponse> agencies = agencyService.getAllAgencies();

        ApiResponse<List<AgencyResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Agencies fetched successfully",
                        agencies);

        return ResponseEntity.ok(apiResponse);
    }


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