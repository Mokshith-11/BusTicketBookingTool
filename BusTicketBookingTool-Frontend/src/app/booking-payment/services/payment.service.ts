import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private http = inject(HttpClient);

  createPayment(payload: any): Observable<any> {
    return this.http.post(`/payments`, payload);
  }

  getPaymentById(paymentId: number): Observable<any> {
    return this.http.get(`/payments/${paymentId}`);
  }

  getCustomerPayments(customerId: number): Observable<any> {
    return this.http.get(`/payments/customers/${customerId}/payments`);
  }

  getBookingPayment(bookingId: number): Observable<any> {
    return this.http.get(`/payments/bookings/${bookingId}/payment`);
  }

  updatePaymentStatus(paymentId: number, status: string): Observable<any> {
    return this.http.patch(`/payments/${paymentId}/status?status=${status}`, {});
  }
}
