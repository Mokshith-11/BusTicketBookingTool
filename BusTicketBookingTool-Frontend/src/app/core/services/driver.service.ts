import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DriverService {
  private http = inject(HttpClient);

  create(officeId: number, payload: any): Observable<any> { return this.http.post(`/offices/${officeId}/drivers`, payload); }
  getByOffice(officeId: number): Observable<any> { return this.http.get(`/offices/${officeId}/drivers`); }
  getById(id: number): Observable<any> { return this.http.get(`/drivers/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/drivers/${id}`, payload); }
  delete(id: number): Observable<any> { return this.http.delete(`/drivers/${id}`); }
}
