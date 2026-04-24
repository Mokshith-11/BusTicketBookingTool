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
/*
 * - This controller is the API entry point for Bus requests from Angular, Postman, or Swagger.
 * - Mapping annotations such as @PostMapping and @GetMapping decide which URL and HTTP method reaches each function.
 * - @Valid checks request DTO rules first; then the controller calls the service and wraps the result in ApiResponse.
 */
public class BusController {

    @Autowired
    private IBusService busService;
    /*
     * POST flow:
     * - Frontend sends JSON data in the request body.
     * - @Valid checks the request DTO before business logic runs.
     * - Service creates/saves the new record and the controller returns CREATED with ApiResponse.
     */
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
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
    /*
     * GET flow:
     * - Frontend asks for existing data using an ID, filter, or list endpoint.
     * - Service reads from the repository and maps entities into response DTOs.
     * - No database data is changed in this request.
     */
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
    /*
     * PUT flow:
     * - URL gives the record ID and the body gives the new values.
     * - @Valid checks the body, then service finds the old record and updates it.
     * - If the ID does not exist, the service throws ResourceNotFoundException.
     */
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


    // DELETE disables or removes a resource by ID.
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