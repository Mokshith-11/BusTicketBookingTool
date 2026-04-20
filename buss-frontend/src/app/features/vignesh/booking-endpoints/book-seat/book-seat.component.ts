import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-book-seat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './book-seat.component.html'
})
export class BookSeatComponent {
  bookingForm: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  isLoading = false;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    // In actual implementation, we might also capture seat number.
    // Sketch says: tripId, customerId
    this.bookingForm = this.fb.group({
      tripId: ['', Validators.required],
      customerId: ['', Validators.required],
      seatNumber: ['1', Validators.required] 
    });
  }

  onSubmit() {
    if (this.bookingForm.valid) {
      this.isLoading = true;
      this.successMessage = null;
      this.errorMessage = null;

      const tripId = this.bookingForm.value.tripId;
      const requestPayload = {
        customerId: this.bookingForm.value.customerId,
        seatNumber: this.bookingForm.value.seatNumber
      };

      // Ensure backend Application is running on port 8081
      const url = `http://localhost:8081/api/v1/trips/${tripId}/bookings`;

      this.http.post<any>(url, requestPayload).subscribe({
        next: (res) => {
          this.isLoading = false;
          // Per sketch requirement: added successfully
          this.successMessage = 'added successfully';
          this.bookingForm.reset();
        },
        error: (err) => {
          this.isLoading = false;
          console.error(err);
          this.errorMessage = err.error?.message || 'Failed to book seat. Ensure backend is running.';
        }
      });
    }
  }
}
