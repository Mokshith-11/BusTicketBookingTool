import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
/*
 * - This Angular service keeps API calls for this feature in one reusable place.
 * - Components call service methods instead of writing HttpClient requests directly in every screen.
 * - The service returns Observables, and the component subscribes to show success/error data in the UI.
 */
export class RouteService {
  private http = inject(HttpClient);

  create(payload: any): Observable<any> { return this.http.post('/routes', payload); }
  getAll(): Observable<any> { return this.http.get('/routes'); }
  getById(id: number): Observable<any> { return this.http.get(`/routes/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/routes/${id}`, payload); }
  delete(id: number): Observable<any> { return this.http.delete(`/routes/${id}`); }
}
