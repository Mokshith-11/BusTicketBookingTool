import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
/*
 * - This Angular service keeps API calls for this feature in one reusable place.
 * - Components call service methods instead of writing HttpClient requests directly in every screen.
 * - The service returns Observables, and the component subscribes to show success/error data in the UI.
 */
export class ReviewService {
  private http = inject(HttpClient);

  create(tripId: number, payload: any): Observable<any> { return this.http.post(`/trips/${tripId}/reviews`, payload); }
  getByTrip(tripId: number): Observable<any> { return this.http.get(`/trips/${tripId}/reviews`); }
  getByCustomer(customerId: number): Observable<any> { return this.http.get(`/customers/${customerId}/reviews`); }
  delete(id: number): Observable<any> { return this.http.delete(`/reviews/${id}`); }
}
