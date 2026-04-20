import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ReviewService {
  private http = inject(HttpClient);

  create(tripId: number, payload: any): Observable<any> { return this.http.post(`/trips/${tripId}/reviews`, payload); }
  getByTrip(tripId: number): Observable<any> { return this.http.get(`/trips/${tripId}/reviews`); }
  getByCustomer(customerId: number): Observable<any> { return this.http.get(`/customers/${customerId}/reviews`); }
  delete(id: number): Observable<any> { return this.http.delete(`/reviews/${id}`); }
}
