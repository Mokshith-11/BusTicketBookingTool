package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.*;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.repository.*;
import com.gemini.BusTicketBookingSystem.service.ITripService;
import com.gemini.BusTicketBookingSystem.dto.request.TripRequest;
import com.gemini.BusTicketBookingSystem.dto.response.TripResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

    @Service
    // Service classes hold the main business flow between controller input and repository calls.
/*
 * - This class contains the real business logic for Trip operations.
 * - It checks rules, loads related records from repositories, throws clear exceptions when something is wrong, and saves valid changes.
 * - At the end it converts entities into response DTOs so controllers can return clean API output.
 */
    public class TripServiceImpl implements ITripService {

        @Autowired
        private ITripRepository tripRepository;

        @Autowired
        private IRouteRepository routeRepository;

        @Autowired
        private IBusRepository busRepository;

        @Autowired
        private IDriverRepositorty driverRepository;

        @Autowired
        private IAddressesRepository addressRepository;

        @Override
        @Transactional
        // Creates a trip only after confirming all linked records really exist.
        public TripResponse createTrip(TripRequest requestDTO) {
            Route route = routeRepository.findById(requestDTO.getRouteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", requestDTO.getRouteId()));

            Bus bus = busRepository.findById(requestDTO.getBusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", requestDTO.getBusId()));

            Driver driver1 = driverRepository.findById(requestDTO.getDriver1Id())
                    .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", requestDTO.getDriver1Id()));

            Driver driver2 = null;
            if (requestDTO.getDriver2Id() != null) {
                driver2 = driverRepository.findById(requestDTO.getDriver2Id())
                        .orElseThrow(() -> new ResourceNotFoundException("Driver", "driverId", requestDTO.getDriver2Id()));
            }


            Addresses boardingAddress = addressRepository.findById(requestDTO.getBoardingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                            requestDTO.getBoardingAddressId()));

            Addresses droppingAddress = addressRepository.findById(requestDTO.getDroppingAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId",
                            requestDTO.getDroppingAddressId()));

            Trip trip = new Trip();
            trip.setRoute(route);
            trip.setBus(bus);
            trip.setDriver1(driver1);
            trip.setDriver2(driver2);
            trip.setBoardingAddress(boardingAddress);
            trip.setDroppingAddress(droppingAddress);
            trip.setDepartureTime(requestDTO.getDepartureTime());
            trip.setArrivalTime(requestDTO.getArrivalTime());
            // A new trip starts with all seats available.
            trip.setAvailableSeats(bus.getCapacity());
            trip.setFare(requestDTO.getFare());
            trip.setTripDate(requestDTO.getTripDate().atStartOfDay());
            trip.setClosed(false);

            Trip savedTrip = tripRepository.save(trip);
            return convertToResponseDTO(savedTrip);
        }

        @Override
        // Returns every trip so the frontend can show the full list screen.
        public List<TripResponse> getAllTrips() {
            return tripRepository.findAll().stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        }

        @Override
        // Fetches one trip by primary key and throws 404-style error if missing.
        public TripResponse getTripById(Integer tripId) {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));
            return convertToResponseDTO(trip);
        }

        @Override
        // Search works by converting a date into a full start/end range for that day.
        public List<TripResponse> searchTrips(String fromCity, String toCity, LocalDate date) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            return tripRepository.findTripsByCitiesAndDateRange(
                            fromCity, toCity, startOfDay, endOfDay).stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional
        // Updates the core trip details used by schedule and booking flows.
        public TripResponse updateTrip(Integer tripId, TripRequest requestDTO) {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

            Route route = routeRepository.findById(requestDTO.getRouteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Route", "routeId", requestDTO.getRouteId()));

            Bus bus = busRepository.findById(requestDTO.getBusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bus", "busId", requestDTO.getBusId()));

            trip.setRoute(route);
            trip.setBus(bus);
            trip.setDepartureTime(requestDTO.getDepartureTime());
            trip.setArrivalTime(requestDTO.getArrivalTime());
            trip.setFare(requestDTO.getFare());
            trip.setTripDate(requestDTO.getTripDate().atStartOfDay());

            Trip updatedTrip = tripRepository.save(trip);
            return convertToResponseDTO(updatedTrip);
        }

        @Override
        @Transactional
        // Closing a trip prevents further booking by marking it closed and clearing remaining seats.
        public void closeTrip(Integer tripId) {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

            trip.setAvailableSeats(0);
            trip.setClosed(true);
            tripRepository.save(trip);
        }

        // Keeps controller responses clean by hiding entity structure behind a response DTO.
        private TripResponse convertToResponseDTO(Trip trip) {
            TripResponse dto = new TripResponse();
            dto.setTripId(trip.getTripId());
            dto.setRouteId(trip.getRoute().getRouteId());
            dto.setFromCity(trip.getRoute().getFromCity());
            dto.setToCity(trip.getRoute().getToCity());
            dto.setBusId(trip.getBus().getBusId());
            dto.setBusRegistrationNumber(trip.getBus().getRegistrationNumber());
            dto.setDepartureTime(trip.getDepartureTime());
            dto.setArrivalTime(trip.getArrivalTime());
            dto.setAvailableSeats(trip.getAvailableSeats());
            dto.setFare(trip.getFare());
            dto.setTripDate(trip.getTripDate());
            dto.setClosed(Boolean.TRUE.equals(trip.getClosed()));
            return dto;
        }
    }
