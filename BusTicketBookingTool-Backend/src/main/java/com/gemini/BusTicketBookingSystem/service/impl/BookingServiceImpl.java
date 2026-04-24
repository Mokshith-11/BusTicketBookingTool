package com.gemini.BusTicketBookingSystem.service.impl;

import com.gemini.BusTicketBookingSystem.entity.Booking;
import com.gemini.BusTicketBookingSystem.entity.Customer;
import com.gemini.BusTicketBookingSystem.exceptions.InvalidOperationException;
import com.gemini.BusTicketBookingSystem.exceptions.ResourceNotFoundException;
import com.gemini.BusTicketBookingSystem.exceptions.SeatNotAvailableException;
import com.gemini.BusTicketBookingSystem.repository.IBookingRepository;
import com.gemini.BusTicketBookingSystem.repository.ITripRepository;
import com.gemini.BusTicketBookingSystem.service.IBookingService;
import com.gemini.BusTicketBookingSystem.dto.request.BookingRequest;
import com.gemini.BusTicketBookingSystem.dto.response.BookingResponse;
import com.gemini.BusTicketBookingSystem.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gemini.BusTicketBookingSystem.repository.ICustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import com.gemini.BusTicketBookingSystem.entity.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
// BookingServiceImpl controls seat reservation rules and seat count updates.
public class BookingServiceImpl implements IBookingService {

        @Autowired
        private IBookingRepository bookingRepository;

        @Autowired
        private ITripRepository tripRepository;

        @Autowired
        private ICustomerRepository customerRepository;

        @Override
        @Transactional
        // Main booking flow: validate trip, validate seat, validate customer, then save booking.
        public BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO) {
                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

                // Seat number must stay inside the physical bus capacity.
                if (requestDTO.getSeatNumber() < 1 ||
                                requestDTO.getSeatNumber() > trip.getBus().getCapacity()) {
                        throw new SeatNotAvailableException(tripId,
                                        "Invalid seat number. Bus capacity is " + trip.getBus().getCapacity());
                }

                // Past trips should not accept new bookings.
                if (trip.getDepartureTime().isBefore(LocalDateTime.now())) {
                        throw new InvalidOperationException("Book Seat",
                                        "Cannot book seat for a trip that has already departed");
                }

                // Only one active booking is allowed for a seat on a trip.
                List<Booking> existingBookings = bookingRepository.findBookingsByTripIdAndStatus(tripId,
                                BookingStatus.Booked);
                boolean seatAlreadyBooked = existingBookings.stream()
                                .anyMatch(b -> b.getSeatNumber().equals(requestDTO.getSeatNumber()));

                if (seatAlreadyBooked) {
                        throw new SeatNotAvailableException(tripId, requestDTO.getSeatNumber());
                }

                // availableSeats is the quick inventory counter used by the UI and backend.
                if (trip.getAvailableSeats() <= 0) {
                        throw new SeatNotAvailableException(tripId, "No seats available for this trip");
                }
                Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer", "customerId", requestDTO.getCustomerId()));

                Booking booking = new Booking();
                booking.setTrip(trip);
                booking.setCustomer(customer);
                booking.setSeatNumber(requestDTO.getSeatNumber());
                booking.setStatus(BookingStatus.Booked);

                // Saving a booking also reduces the trip's remaining seat count.
                trip.setAvailableSeats(trip.getAvailableSeats() - 1);
                tripRepository.save(trip);

                Booking savedBooking = bookingRepository.save(booking);
                return convertToResponseDTO(savedBooking);
        }

        @Override
        // Used by the customer booking history screen.
        public List<BookingResponse> getCustomerBookings(Integer customerId) {
                if (!customerRepository.existsById(customerId)) {
                        throw new ResourceNotFoundException("Customer", "customerId", customerId);
                }

                return bookingRepository.findBookingsByCustomerId(customerId).stream()
                                .map(this::convertToResponseDTO)
                                .collect(Collectors.toList());
        }

        @Override
        // Returns one booking with linked trip and customer details flattened into a DTO.
        public BookingResponse getBookingById(Integer bookingId) {
                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));
                return convertToResponseDTO(booking);
        }

        @Override
        @Transactional
        // Cancelling a booking flips its status and gives the seat back to the trip.
        public void cancelBooking(Integer bookingId) {
                Booking booking = bookingRepository.findById(bookingId)
                                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

                if (booking.getStatus() == BookingStatus.Available) {
                        throw new InvalidOperationException("Cancel Booking",
                                        "Booking is already cancelled or not booked");
                }

                booking.setStatus(BookingStatus.Available);

                // The seat becomes reusable for future bookings on the same trip.
                Trip trip = booking.getTrip();
                trip.setAvailableSeats(trip.getAvailableSeats() + 1);
                tripRepository.save(trip);

                bookingRepository.save(booking);
        }

        @Override
        // Builds a list of free seat numbers by removing booked seats from the full seat range.
        public List<Integer> getAvailableSeats(Integer tripId) {
                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

                List<Booking> bookedSeats = bookingRepository.findBookingsByTripIdAndStatus(tripId,
                                BookingStatus.Booked);
                List<Integer> bookedSeatNumbers = bookedSeats.stream()
                                .map(Booking::getSeatNumber)
                                .collect(Collectors.toList());

                return java.util.stream.IntStream.rangeClosed(1, trip.getBus().getCapacity())
                                .boxed()
                                .filter(seat -> !bookedSeatNumbers.contains(seat))
                                .collect(Collectors.toList());
        }

        @Override
        // Returns only booked seat numbers so the frontend can mark them as unavailable.
        public List<Integer> getBookedSeats(Integer tripId) {
                Trip trip = tripRepository.findById(tripId)
                                .orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

                return bookingRepository.findBookingsByTripIdAndStatus(tripId, BookingStatus.Booked).stream()
                                .map(Booking::getSeatNumber)
                                .collect(Collectors.toList());
        }

        // Converts entity data into the compact response shape expected by the frontend.
        private BookingResponse convertToResponseDTO(Booking booking) {
                BookingResponse dto = new BookingResponse();
                dto.setBookingId(booking.getBookingId());
                dto.setTripId(booking.getTrip().getTripId());
                dto.setSeatNumber(booking.getSeatNumber());
                dto.setStatus(booking.getStatus().toString());
                if (booking.getCustomer() != null) {
                        dto.setCustomerId(booking.getCustomer().getCustomerId());
                        dto.setCustomerName(booking.getCustomer().getName());
                }
                return dto;
        }
}
