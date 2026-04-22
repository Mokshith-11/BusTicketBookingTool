import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private http = inject(HttpClient);

  create(payload: any): Observable<any> { return this.http.post('/customers', payload); }
  getById(id: number): Observable<any> { return this.http.get(`/customers/${id}`); }
  update(id: number, payload: any): Observable<any> { return this.http.put(`/customers/${id}`, payload); }
  getAll(): Observable<any> { return this.http.get('/customers'); }
}
