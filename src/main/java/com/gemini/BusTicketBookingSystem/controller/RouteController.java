package com.gemini.BusTicketBookingSystem.controller;


import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;
import com.gemini.BusTicketBookingSystem.service.IRouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private IRouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteRequest requestDTO) {
        RouteResponse response = routeService.createRoute(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        List<RouteResponse> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Integer routeId) {
        RouteResponse response = routeService.getRouteById(routeId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{routeId}")
    public ResponseEntity<RouteResponse> updateRoute(
            @PathVariable Integer routeId,
            @Valid @RequestBody RouteRequest requestDTO) {
        RouteResponse response = routeService.updateRoute(routeId, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> disableRoute(@PathVariable Integer routeId) {
        routeService.disableRoute(routeId);
        return ResponseEntity.noContent().build();
    }
}