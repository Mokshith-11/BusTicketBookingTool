package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.BusRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BusResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IBusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BusController {

    @Autowired
    private IBusService busService;


    @PostMapping("/offices/{officeId}/buses")
    public ResponseEntity<ApiResponse<BusResponse>> registerBus(
            @PathVariable Integer officeId,
            @Valid @RequestBody BusRequest requestDTO) {

        BusResponse response = busService.registerBus(officeId, requestDTO);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Bus registered successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/offices/{officeId}/buses")
    public ResponseEntity<ApiResponse<List<BusResponse>>> getBusesByOffice(
            @PathVariable Integer officeId) {

        List<BusResponse> buses = busService.getBusesByOffice(officeId);

        ApiResponse<List<BusResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Buses fetched successfully",
                        buses);

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<BusResponse>> getBusById(
            @PathVariable Integer busId) {

        BusResponse response = busService.getBusById(busId);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<BusResponse>> updateBus(
            @PathVariable Integer busId,
            @Valid @RequestBody BusRequest requestDTO) {

        BusResponse response = busService.updateBus(busId, requestDTO);

        ApiResponse<BusResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/buses/{busId}")
    public ResponseEntity<ApiResponse<String>> retireBus(
            @PathVariable Integer busId) {

        busService.retireBus(busId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Bus retired successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}