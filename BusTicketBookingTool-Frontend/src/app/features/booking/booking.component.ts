import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BookingService } from '../../core/services/booking.service';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-booking',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './booking.component.html'
})
export class BookingComponent implements OnInit {
  bookingService = inject(BookingService);
  authService = inject(AuthService);
  fb = inject(FormBuilder);
  router = inject(Router);

  bookings = signal<any[]>([]);

  bookForm = this.fb.group({
    tripId: ['', Validators.required],
    seatNumber: ['', Validators.required],
    passengerName: ['', Validators.required]
  });

  ngOnInit() { this.loadBookings(); }

  loadBookings() {
    const custId = this.authService.customerId() || 1;
    this.bookingService.getCustomerBookings(custId).subscribe({
      next: (res) => this.bookings.set(res?.data || []),
      error: () => this.bookings.set([])
    });
  }

  onBook() {
    if (this.bookForm.valid) {
      const tripId = Number(this.bookForm.value.tripId);
      this.bookingService.createBooking(tripId, this.bookForm.value).subscribe({
        next: () => { this.loadBookings(); this.bookForm.reset(); },
        error: () => {}
      });
    }
  }

  onCancel(id: number) {
    this.bookingService.cancelBooking(id).subscribe({
      next: () => this.loadBookings(),
      error: () => {}
    });
  }

  back() { this.router.navigate(['/dashboard']); }
}
