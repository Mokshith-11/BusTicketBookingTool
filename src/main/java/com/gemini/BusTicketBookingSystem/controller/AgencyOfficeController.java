package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyOfficeRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyOfficeResponse;
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
    public ResponseEntity<AgencyOfficeResponse> createOffice(
            @PathVariable Integer agencyId,
            @Valid @RequestBody AgencyOfficeRequest requestDTO) {
        AgencyOfficeResponse response = officeService.addOffice(agencyId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/agencies/{agencyId}/offices")
    public ResponseEntity<List<AgencyOfficeResponse>> getOfficesByAgency(@PathVariable Integer agencyId) {
        List<AgencyOfficeResponse> offices = officeService.getOfficesByAgency(agencyId);
        return ResponseEntity.ok(offices);
    }

    @GetMapping("/offices/{officeId}")
    public ResponseEntity<AgencyOfficeResponse> getOfficeById(@PathVariable Integer officeId) {
        AgencyOfficeResponse response = officeService.getOfficeById(officeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/offices/{officeId}")
    public ResponseEntity<AgencyOfficeResponse> updateOffice(
            @PathVariable Integer officeId,
            @Valid @RequestBody AgencyOfficeRequest requestDTO) {
        AgencyOfficeResponse response = officeService.updateOffice(officeId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/offices/{officeId}")
    public ResponseEntity<Void> deactivateOffice(@PathVariable Integer officeId) {
        officeService.deleteOffice(officeId);
        return ResponseEntity.noContent().build();
    }
}

