import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../core/auth.service';
import { MODULE_CONFIGS, ModuleConfig, EndpointDef } from '../../core/config/module-endpoints.config';

@Component({
  selector: 'app-module-endpoints',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './module-endpoints.component.html'
})
export class ModuleEndpointsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  config = signal<ModuleConfig | null>(null);
  activeEndpoint = signal<EndpointDef | null>(null);
  paramValues: { [key: string]: string } = {};
  bodyValues: { [key: string]: string } = {};
  queryValues: { [key: string]: string } = {};
  response = signal<string>('');
  responseStatus = signal<'success' | 'error' | ''>('');
  loading = signal(false);

  ngOnInit() {
    const moduleKey = this.route.snapshot.data['moduleKey'];
    if (moduleKey && MODULE_CONFIGS[moduleKey]) {
      const conf = { ...MODULE_CONFIGS[moduleKey] };
      const user = this.auth.username()?.toLowerCase();
      // Dynamically determine ownership
      conf.isMine = conf.owner.toLowerCase() === user;
      this.config.set(conf);
    }
  }

  selectEndpoint(ep: EndpointDef) {
    this.activeEndpoint.set(ep);
    this.paramValues = {};
    this.bodyValues = {};
    this.queryValues = {};
    this.response.set('');
    this.responseStatus.set('');
  }

  getMethodColor(method: string): string {
    switch (method) {
      case 'GET': return 'bg-green-500';
      case 'POST': return 'bg-blue-500';
      case 'PUT': return 'bg-yellow-500';
      case 'PATCH': return 'bg-purple-500';
      case 'DELETE': return 'bg-red-500';
      default: return 'bg-gray-500';
    }
  }

  buildUrl(ep: EndpointDef): string {
    let url = ep.path;
    // Replace path params like {tripId} with actual values
    for (const param of ep.params) {
      url = url.replace(`{${param.name}}`, this.paramValues[param.name] || '0');
    }
    // Append query params
    if (ep.queryParams && ep.queryParams.length > 0) {
      const qp = ep.queryParams
        .map(q => `${q.name}=${encodeURIComponent(this.queryValues[q.name] || '')}`)
        .join('&');
      url += `?${qp}`;
    }
    return url;
  }

  submit() {
    const ep = this.activeEndpoint();
    if (!ep) return;

    this.loading.set(true);
    this.response.set('');
    const url = this.buildUrl(ep);

    // Build body for POST/PUT/PATCH
    let body: Record<string, unknown> | null = null;
    if (ep.bodyFields && ep.bodyFields.length > 0) {
      body = {};
      for (const field of ep.bodyFields) {
        const val = this.bodyValues[field.name];
        body[field.name] = field.type === 'number' ? Number(val) : val;
      }
    }

    let req$: Observable<unknown>;
    switch (ep.method) {
      case 'GET': req$ = this.http.get(url); break;
      case 'POST': req$ = this.http.post(url, body); break;
      case 'PUT': req$ = this.http.put(url, body); break;
      case 'PATCH': req$ = this.http.patch(url, body || {}); break;
      case 'DELETE': req$ = this.http.delete(url); break;
      default: return;
    }

    req$.subscribe({
      next: (res: unknown) => {
        this.response.set(JSON.stringify(res, null, 2));
        this.responseStatus.set('success');
        this.loading.set(false);
      },
      error: (err: { error?: unknown; message?: string }) => {
        const errBody = err.error || err.message || 'Request failed';
        this.response.set(typeof errBody === 'string' ? errBody : JSON.stringify(errBody, null, 2));
        this.responseStatus.set('error');
        this.loading.set(false);
      }
    });
  }

  back() {
    this.router.navigate(['/dashboard']);
  }
}
