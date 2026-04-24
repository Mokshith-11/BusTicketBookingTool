import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
/*
 * Beginner guide:
 * - This Angular service keeps API calls for this feature in one reusable place.
 * - Components call service methods instead of writing HttpClient requests directly in every screen.
 * - The service returns Observables, and the component subscribes to show success/error data in the UI.
 */
export class BookingService {
  private http = inject(HttpClient);

  createBooking(tripId: number, payload: any): Observable<any> {
    return this.http.post(`/trips/${tripId}/bookings`, payload);
  }

  getCustomerBookings(customerId: number): Observable<any> {
    return this.http.get(`/customers/${customerId}/bookings`);
  }

  getBookingById(bookingId: number): Observable<any> {
    return this.http.get(`/bookings/${bookingId}`);
  }

  cancelBooking(bookingId: number): Observable<any> {
    return this.http.patch(`/bookings/${bookingId}/cancel`, {});
  }

  getAvailableSeats(tripId: number): Observable<any> {
    return this.http.get(`/trips/${tripId}/seats/available`);
  }

  getBookedSeats(tripId: number): Observable<any> {
    return this.http.get(`/trips/${tripId}/seats/booked`);
  }
}
