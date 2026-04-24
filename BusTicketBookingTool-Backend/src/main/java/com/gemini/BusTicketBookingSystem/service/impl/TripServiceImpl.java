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


/**
 * Service implementation for managing trips.
 * Contains business logic for creating, retrieving, searching,
 * updating, and closing trips. A trip connects a route, bus,
 * drivers, and addresses with a schedule and fare.
 */
    @Service
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

        /**
         * Creates a new trip in the system.
         * Validates that all referenced entities exist (route, bus, drivers, addresses).
         * Sets the available seats to the bus's full capacity.
         * Driver 2 is optional — only loaded if a driver2Id is provided.
         * This method is transactional to ensure data consistency.
         *
         * @param requestDTO - contains routeId, busId, driver1Id, driver2Id (optional),
         *                     boardingAddressId, droppingAddressId, departureTime,
         *                     arrivalTime, fare, tripDate
         * @return TripResponse - the created trip data with generated ID
         */
        @Override
        @Transactional
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
            trip.setAvailableSeats(bus.getCapacity()); // Initialize with full capacity
            trip.setFare(requestDTO.getFare());
            trip.setTripDate(requestDTO.getTripDate().atStartOfDay());
            trip.setClosed(false);

            Trip savedTrip = tripRepository.save(trip);
            return convertToResponseDTO(savedTrip);
        }

        /**
         * Retrieves all trips stored in the database.
         * Maps each Trip entity to a TripResponse DTO.
         *
         * @return List of TripResponse - all trips
         */
        @Override
        public List<TripResponse> getAllTrips() {
            return tripRepository.findAll().stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        }

        /**
         * Retrieves a single trip by its unique trip ID.
         * Throws ResourceNotFoundException if no trip exists with that ID.
         *
         * @param tripId - the unique ID of the trip
         * @return TripResponse - the trip details
         */
        @Override
        public TripResponse getTripById(Integer tripId) {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));
            return convertToResponseDTO(trip);
        }

        /**
         * Searches for trips by departure city, destination city, and date.
         * Finds all trips whose route matches the fromCity-toCity pair
         * and whose trip date falls within the given day (midnight to 23:59:59).
         * This is the main search method used by customers to find available buses.
         *
         * @param fromCity - the departure city name
         * @param toCity   - the destination city name
         * @param date     - the travel date to search for
         * @return List of TripResponse - matching trips
         */
        @Override
        public List<TripResponse> searchTrips(String fromCity, String toCity, LocalDate date) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            return tripRepository.findTripsByCitiesAndDateRange(
                            fromCity, toCity, startOfDay, endOfDay).stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        }

        /**
         * Updates an existing trip's details.
         * Finds the trip by ID, validates that the new route and bus exist,
         * then updates route, bus, times, fare, and date.
         * Note: Does not update driver assignments.
         * This method is transactional to ensure data consistency.
         *
         * @param tripId     - the ID of the trip to update
         * @param requestDTO - the new trip data
         * @return TripResponse - the updated trip data
         */
        @Override
        @Transactional
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

        /**
         * Closes a trip so that no more bookings can be made.
         * Sets the available seats to 0, effectively blocking new bookings.
         * Throws ResourceNotFoundException if the trip doesn't exist.
         * This method is transactional to ensure data consistency.
         *
         * @param tripId - the ID of the trip to close
         */
        @Override
        @Transactional
        public void closeTrip(Integer tripId) {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

            trip.setAvailableSeats(0);
            trip.setClosed(true);
            tripRepository.save(trip);
        }

        /**
         * Helper method to convert a Trip entity to a TripResponse DTO.
         * Maps trip details including route info (from/to cities),
         * bus info (ID, registration number), times, seats, fare, and date.
         *
         * @param trip - the Trip entity to convert
         * @return TripResponse - the mapped DTO
         */
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
