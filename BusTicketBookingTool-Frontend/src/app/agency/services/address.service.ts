import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AddressService {
  private http = inject(HttpClient);

  create(payload: any): Observable<any> { return this.http.post('/addresses', payload); }
  getById(id: number): Observable<any> { return this.http.get(`/addresses/${id}`); }
}
