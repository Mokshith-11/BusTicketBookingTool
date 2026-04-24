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
export class TripService {
  private http = inject(HttpClient);

  create(payload: any): Observable<any> { return this.http.post('/trips', payload); }
  getAll(): Observable<any> { return this.http.get('/trips'); }
  getById(id: number): Observable<any> { return this.http.get(`/trips/${id}`); }
  search(fromCity: string, toCity: string, date: string): Observable<any> {
    return this.http.get(`/trips/search?fromCity=${fromCity}&toCity=${toCity}&date=${date}`);
  }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/trips/${id}`, payload); }
  close(id: number): Observable<any> { return this.http.patch(`/trips/${id}/close`, {}); }
}
