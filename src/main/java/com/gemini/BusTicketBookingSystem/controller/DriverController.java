package com.gemini.BusTicketBookingSystem.controller;

import com.gemini.BusTicketBookingSystem.dto.request.DriverRequest;
import com.gemini.BusTicketBookingSystem.dto.response.DriverResponse;
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
    public ResponseEntity<DriverResponse> registerDriver(
            @PathVariable Integer officeId,
            @Valid @RequestBody DriverRequest requestDTO) {
        DriverResponse response = driverService.registerDriver(officeId, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/offices/{officeId}/drivers")
    public ResponseEntity<List<DriverResponse>> getDriversByOffice(@PathVariable Integer officeId) {
        List<DriverResponse> drivers = driverService.getDriversByOffice(officeId);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/drivers/{driverId}")
    public ResponseEntity<DriverResponse> getDriverById(@PathVariable Integer driverId) {
        DriverResponse response = driverService.getDriverById(driverId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/drivers/{driverId}")
    public ResponseEntity<DriverResponse> updateDriver(
            @PathVariable Integer driverId,
            @Valid @RequestBody DriverRequest requestDTO) {
        DriverResponse response = driverService.updateDriver(driverId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/drivers/{driverId}")
    public ResponseEntity<Void> removeDriver(@PathVariable Integer driverId) {
        driverService.removeDriver(driverId);
        return ResponseEntity.noContent().build();
    }
}
