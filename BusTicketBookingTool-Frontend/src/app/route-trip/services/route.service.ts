import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class RouteService {
  private http = inject(HttpClient);

  create(payload: any): Observable<any> { return this.http.post('/routes', payload); }
  getAll(): Observable<any> { return this.http.get('/routes'); }
  getById(id: number): Observable<any> { return this.http.get(`/routes/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/routes/${id}`, payload); }
  delete(id: number): Observable<any> { return this.http.delete(`/routes/${id}`); }
}
