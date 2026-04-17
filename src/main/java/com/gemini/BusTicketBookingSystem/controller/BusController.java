package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;
import com.gemini.BusTicketBookingSystem.service.IBusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;




@RestController
@RequestMapping("/api/v1")
public class BusController {

    @Autowired
    private IBusService busService;

    @PostMapping("/offices/{officeId}/buses")
    public ResponseEntity<BusResponse> registerBus(
            @PathVariable Integer officeId,
            @Valid @RequestBody BusRequest requestDTO) {
        BusResponse response = busService.registerBus(officeId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/offices/{officeId}/buses")
    public ResponseEntity<List<BusResponse>> getBusesByOffice(@PathVariable Integer officeId) {
        List<BusResponse> buses = busService.getBusesByOffice(officeId);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/buses/{busId}")
    public ResponseEntity<BusResponse> getBusById(@PathVariable Integer busId) {
        BusResponse response = busService.getBusById(busId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/buses/{busId}")
    public ResponseEntity<BusResponse> updateBus(
            @PathVariable Integer busId,
            @Valid @RequestBody BusRequest requestDTO) {
        BusResponse response = busService.updateBus(busId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/buses/{busId}")
    public ResponseEntity<Void> retireBus(@PathVariable Integer busId) {
        busService.retireBus(busId);
        return ResponseEntity.noContent().build();
    }
}