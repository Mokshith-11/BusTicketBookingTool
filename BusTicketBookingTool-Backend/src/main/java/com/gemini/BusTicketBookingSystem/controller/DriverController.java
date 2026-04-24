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
/*
 * - This controller is the API entry point for Driver requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class DriverController {

    @Autowired
    private IDriverService driverService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
     */
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
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
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
     */
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


    // DELETE disables or removes a resource by ID.
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
