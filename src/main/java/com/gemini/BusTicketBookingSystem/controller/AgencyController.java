package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.AgencyRequest;
import com.gemini.BusTicketBookingSystem.dto.response.AgencyResponse;
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
    public ResponseEntity<AgencyResponse> createAgency(@Valid @RequestBody AgencyRequest requestDTO) {
        AgencyResponse response = agencyService.createAgency(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AgencyResponse>> getAllAgencies() {
        List<AgencyResponse> agencies = agencyService.getAllAgencies();
        return ResponseEntity.ok(agencies);
    }

    @GetMapping("/{agencyId}")
    public ResponseEntity<AgencyResponse> getAgencyById(@PathVariable Integer agencyId) {
        AgencyResponse response = agencyService.getAgencyById(agencyId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{agencyId}")
    public ResponseEntity<AgencyResponse> updateAgency(
            @PathVariable Integer agencyId,
            @Valid @RequestBody AgencyRequest requestDTO) {
        AgencyResponse response = agencyService.updateAgency(agencyId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{agencyId}")
    public ResponseEntity<Void> deactivateAgency(@PathVariable Integer agencyId) {
        agencyService.deleteAgency(agencyId);
        return ResponseEntity.noContent().build();
    }
}
