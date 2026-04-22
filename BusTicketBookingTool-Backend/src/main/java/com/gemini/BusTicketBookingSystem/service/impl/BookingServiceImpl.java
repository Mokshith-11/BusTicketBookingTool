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

/**
 * Service implementation for managing seat bookings.
 * Contains the core business logic for booking seats on trips,
 * cancelling bookings, and checking seat availability.
 */
@Service
public class BookingServiceImpl implements IBookingService {

	@Autowired
	private IBookingRepository bookingRepository;

	@Autowired
	private ITripRepository tripRepository;

	@Autowired
	private ICustomerRepository customerRepository;

	/**
	 * Books a seat on a specific trip for a customer.
	 * This method performs several validations before creating a booking:
	 * 1. Checks that the trip exists
	 * 2. Validates the seat number is within the bus capacity range
	 * 3. Ensures the trip hasn't already departed
	 * 4. Verifies the specific seat isn't already booked
	 * 5. Confirms there are available seats on the trip
	 * 6. Validates the customer exists
	 * After passing all checks, it creates the booking and reduces available seats by 1.
	 * This method is transactional — if any part fails, all changes are rolled back.
	 *
	 * @param tripId     - the ID of the trip to book on
	 * @param requestDTO - contains customerId and seatNumber
	 * @return BookingResponse - the confirmed booking details
	 */
	@Override
	@Transactional
	public BookingResponse bookSeat(Integer tripId, BookingRequest requestDTO) {
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

		// Validate seat number is within the valid range (1 to bus capacity)
		if (requestDTO.getSeatNumber() < 1 ||
				requestDTO.getSeatNumber() > trip.getBus().getCapacity()) {
			throw new SeatNotAvailableException(tripId,
					"Invalid seat number. Bus capacity is " + trip.getBus().getCapacity());
		}

		// Check if trip is still open for booking (hasn't departed yet)
		if (trip.getDepartureTime().isBefore(LocalDateTime.now())) {
			throw new InvalidOperationException("Book Seat",
					"Cannot book seat for a trip that has already departed");
		}

		// Check if the requested seat is already booked by someone else
		List<Booking> existingBookings = bookingRepository.findBookingsByTripIdAndStatus(tripId,
				BookingStatus.Booked);
		boolean seatAlreadyBooked = existingBookings.stream()
				.anyMatch(b -> b.getSeatNumber().equals(requestDTO.getSeatNumber()));

		if (seatAlreadyBooked) {
			throw new SeatNotAvailableException(tripId, requestDTO.getSeatNumber());
		}

		// Check if trip has any available seats left
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

		// Decrease available seat count by 1 after successful booking
		trip.setAvailableSeats(trip.getAvailableSeats() - 1);
		tripRepository.save(trip);

		Booking savedBooking = bookingRepository.save(booking);
		return convertToResponseDTO(savedBooking);
	}

	/**
	 * Retrieves all bookings made by a specific customer.
	 * First checks that the customer exists, then fetches all their bookings.
	 *
	 * @param customerId - the ID of the customer
	 * @return List of BookingResponse - all bookings for that customer
	 */
	@Override
	public List<BookingResponse> getCustomerBookings(Integer customerId) {
		if (!customerRepository.existsById(customerId)) {
			throw new ResourceNotFoundException("Customer", "customerId", customerId);
		}

		return bookingRepository.findBookingsByCustomerId(customerId).stream()
				.map(this::convertToResponseDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves a single booking by its unique booking ID.
	 * Throws ResourceNotFoundException if no booking exists with that ID.
	 *
	 * @param bookingId - the unique ID of the booking
	 * @return BookingResponse - the booking details
	 */
	@Override
	public BookingResponse getBookingById(Integer bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));
		return convertToResponseDTO(booking);
	}

	/**
	 * Cancels an existing booking.
	 * Changes the booking status from "Booked" to "Available" and
	 * adds the seat back to the trip's available seats count.
	 * Throws InvalidOperationException if the booking is already cancelled.
	 * This method is transactional to ensure both the booking and trip are updated together.
	 *
	 * @param bookingId - the ID of the booking to cancel
	 */
	@Override
	@Transactional
	public void cancelBooking(Integer bookingId) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

		if (booking.getStatus() == BookingStatus.Available) {
			throw new InvalidOperationException("Cancel Booking",
					"Booking is already cancelled or not booked");
		}

		booking.setStatus(BookingStatus.Available);

		// Increase available seat count by 1 since the seat is freed up
		Trip trip = booking.getTrip();
		trip.setAvailableSeats(trip.getAvailableSeats() + 1);
		tripRepository.save(trip);

		bookingRepository.save(booking);
	}

	/**
	 * Returns a list of available (unbooked) seat numbers for a trip.
	 * Calculates this by generating all seat numbers (1 to bus capacity)
	 * and removing the ones that are already booked.
	 *
	 * @param tripId - the ID of the trip to check
	 * @return List of Integer - available seat numbers
	 */
	@Override
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

	/**
	 * Returns a list of seat numbers that are currently booked on a trip.
	 * Only includes seats with "Booked" status (not cancelled ones).
	 *
	 * @param tripId - the ID of the trip to check
	 * @return List of Integer - booked seat numbers
	 */
	@Override
	public List<Integer> getBookedSeats(Integer tripId) {
		Trip trip = tripRepository.findById(tripId)
				.orElseThrow(() -> new ResourceNotFoundException("Trip", "tripId", tripId));

		return bookingRepository.findBookingsByTripIdAndStatus(tripId, BookingStatus.Booked).stream()
				.map(Booking::getSeatNumber)
				.collect(Collectors.toList());
	}

	/**
	 * Helper method to convert a Booking entity to a BookingResponse DTO.
	 * Maps booking ID, trip ID, seat number, status, and customer info.
	 *
	 * @param booking - the Booking entity to convert
	 * @return BookingResponse - the mapped DTO
	 */
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