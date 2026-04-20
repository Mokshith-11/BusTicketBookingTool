package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;
import com.gemini.BusTicketBookingSystem.dto.response.ApiResponse;
import com.gemini.BusTicketBookingSystem.service.IDriverService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DriverController {

    @Autowired
    private IDriverService driverService;


    @PostMapping("/offices/{officeId}/drivers")
    public ResponseEntity<ApiResponse<DriverResponse>> registerDriver(
            @PathVariable Integer officeId,
            @Valid @RequestBody DriverRequest requestDTO) {

        DriverResponse response = driverService.registerDriver(officeId, requestDTO);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Driver registered successfully",
                        response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/offices/{officeId}/drivers")
    public ResponseEntity<ApiResponse<List<DriverResponse>>> getDriversByOffice(
            @PathVariable Integer officeId) {

        List<DriverResponse> drivers = driverService.getDriversByOffice(officeId);

        ApiResponse<List<DriverResponse>> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Drivers fetched successfully",
                        drivers);

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponse>> getDriverById(
            @PathVariable Integer driverId) {

        DriverResponse response = driverService.getDriverById(driverId);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver fetched successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<DriverResponse>> updateDriver(
            @PathVariable Integer driverId,
            @Valid @RequestBody DriverRequest requestDTO) {

        DriverResponse response = driverService.updateDriver(driverId, requestDTO);

        ApiResponse<DriverResponse> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver updated successfully",
                        response);

        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<ApiResponse<String>> removeDriver(
            @PathVariable Integer driverId) {

        driverService.removeDriver(driverId);

        ApiResponse<String> apiResponse =
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Driver removed successfully",
                        null);

        return ResponseEntity.ok(apiResponse);
    }
}
