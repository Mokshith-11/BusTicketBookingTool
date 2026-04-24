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
export class AgencyOfficeService {
  private http = inject(HttpClient);

  create(agencyId: number, payload: any): Observable<any> { return this.http.post(`/agencies/${agencyId}/offices`, payload); }
  getByAgency(agencyId: number): Observable<any> { return this.http.get(`/agencies/${agencyId}/offices`); }
  getById(id: number): Observable<any> { return this.http.get(`/offices/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/offices/${id}`, payload); }
  delete(id: number): Observable<any> { return this.http.delete(`/offices/${id}`); }
}
