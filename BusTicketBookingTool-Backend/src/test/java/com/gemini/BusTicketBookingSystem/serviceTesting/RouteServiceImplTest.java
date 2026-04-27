package com.gemini.BusTicketBookingSystem.serviceTesting;



import com.gemini.BusTicketBookingSystem.dto.request.RouteRequest;
import com.gemini.BusTicketBookingSystem.dto.response.RouteResponse;
import com.gemini.BusTicketBookingSystem.entity.Route;
import com.gemini.BusTicketBookingSystem.exceptions.DuplicateResourceException;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.IRouteRepository;
import com.gemini.BusTicketBookingSystem.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {

    @Mock
    private IRouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    // -----------------------------
    // Helper methods (mock objects)
    // -----------------------------
    private Route mockRoute() {
        Route route = new Route();
        route.setRouteId(1);
        route.setFromCity("Chennai");
        route.setToCity("Bangalore");

        return route;
    }

    private RouteRequest mockRequest() {
        RouteRequest request = new RouteRequest();
        request.setFromCity("Chennai");
        request.setToCity("Bangalore");

        return request;
    }

    // -----------------------------
    // CREATE
    // -----------------------------
    @Test
    void createRoute_success() {
        RouteRequest request = mockRequest();
        Route savedRoute = mockRoute();

        when(routeRepository.findByFromCityAndToCity("Chennai", "Bangalore"))
                .thenReturn(Collections.emptyList());
        when(routeRepository.save(any(Route.class))).thenReturn(savedRoute);

        RouteResponse response = routeService.createRoute(request);

        assertNotNull(response);
        assertEquals("Chennai", response.getFromCity());

        verify(routeRepository).save(any(Route.class));
    }

    @Test
    void createRoute_sameCity_throwsException() {
        RouteRequest request = mockRequest();
        request.setToCity("Chennai");

        assertThrows(InvalidOperationException.class,
                () -> routeService.createRoute(request));

        verify(routeRepository, never()).save(any());
    }

    @Test
    void createRoute_duplicate_throwsException() {
        RouteRequest request = mockRequest();

        when(routeRepository.findByFromCityAndToCity("Chennai", "Bangalore"))
                .thenReturn(List.of(mockRoute()));

        assertThrows(DuplicateResourceException.class,
                () -> routeService.createRoute(request));

        verify(routeRepository, never()).save(any());
    }

    // -----------------------------
    // GET ALL
    // -----------------------------
    @Test
    void getAllRoutes_success() {
        when(routeRepository.findAll())
                .thenReturn(List.of(mockRoute()));

        List<RouteResponse> response = routeService.getAllRoutes();

        assertEquals(1, response.size());
        verify(routeRepository).findAll();
    }

    // -----------------------------
    // GET BY ID
    // -----------------------------
    @Test
    void getRouteById_success() {
        when(routeRepository.findById(1))
                .thenReturn(Optional.of(mockRoute()));

        RouteResponse response = routeService.getRouteById(1);

        assertEquals("Chennai", response.getFromCity());
        verify(routeRepository).findById(1);
    }

    @Test
    void getRouteById_notFound() {
        when(routeRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> routeService.getRouteById(1));

        verify(routeRepository).findById(1);
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    @Test
    void updateRoute_success() {
        RouteRequest request = mockRequest();
        Route existing = mockRoute();

        when(routeRepository.findById(1)).thenReturn(Optional.of(existing));
        when(routeRepository.findByFromCityAndToCity("Chennai", "Bangalore"))
                .thenReturn(Collections.emptyList());
        when(routeRepository.save(any(Route.class))).thenReturn(existing);

        RouteResponse response = routeService.updateRoute(1, request);

        assertEquals("Chennai", response.getFromCity());
        verify(routeRepository).save(existing);
    }

    @Test
    void updateRoute_notFound() {
        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> routeService.updateRoute(1, mockRequest()));

        verify(routeRepository, never()).save(any());
    }
    // -----------------------------
    // DELETE (disableRoute)
    // -----------------------------
}
