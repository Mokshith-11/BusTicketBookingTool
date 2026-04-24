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
export class BusService {
  private http = inject(HttpClient);

  create(officeId: number, payload: any): Observable<any> { return this.http.post(`/offices/${officeId}/buses`, payload); }
  getByOffice(officeId: number): Observable<any> { return this.http.get(`/offices/${officeId}/buses`); }
  getById(id: number): Observable<any> { return this.http.get(`/buses/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/buses/${id}`, payload); }
  delete(id: number): Observable<any> { return this.http.delete(`/buses/${id}`); }
}
