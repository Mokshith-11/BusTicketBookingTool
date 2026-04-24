import { Component, Input, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../../core/services/auth.service';
import { MODULE_CONFIGS, ModuleConfig, EndpointDef } from '../../../core/config/module-endpoints.config';

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

  // Signals keep the UI reactive without manual change detection code.
  config = signal<ModuleConfig | null>(null);
  activeEndpoint = signal<EndpointDef | null>(null);
  paramValues: { [key: string]: string } = {};
  bodyValues: { [key: string]: string } = {};
  queryValues: { [key: string]: string } = {};
  response = signal<string>('');
  responseStatus = signal<'success' | 'error' | ''>('');
  loading = signal(false);
  @Input() moduleKey?: string;

  ngOnInit() {
    const moduleKey = this.moduleKey || this.route.snapshot.data['moduleKey'];
    if (moduleKey && MODULE_CONFIGS[moduleKey]) {
      const conf = { ...MODULE_CONFIGS[moduleKey] };
      const user = this.auth.username()?.toLowerCase();
      // isMine lets the UI highlight modules owned by the logged-in team member.
      conf.isMine = conf.owner.toLowerCase() === user;
      this.config.set(conf);
    }
  }

  // Selecting a new endpoint resets old form values and old response output.
  selectEndpoint(ep: EndpointDef) {
    this.activeEndpoint.set(ep);
    this.paramValues = {};
    this.bodyValues = {};
    this.queryValues = {};
    this.response.set('');
    this.responseStatus.set('');
  }

  // Colors make HTTP methods easy to scan visually in the left-side API list.
  getMethodColor(method: string): string {
    switch (method) {
      case 'GET': return 'bg-teal-500 shadow-teal-500/40';
      case 'POST': return 'bg-indigo-500 shadow-indigo-500/40';
      case 'PUT': return 'bg-amber-500 shadow-amber-500/40';
      case 'PATCH': return 'bg-violet-500 shadow-violet-500/40';
      case 'DELETE': return 'bg-rose-500 shadow-rose-500/40';
      default: return 'bg-slate-500';
    }
  }

  // Converts endpoint templates like /trips/{tripId} into the real URL sent to the backend.
  buildUrl(ep: EndpointDef): string {
    let url = ep.path;
    // Replace path params like {tripId} with actual values entered in the UI.
    for (const param of ep.params) {
      url = url.replace(`{${param.name}}`, this.paramValues[param.name] || '0');
    }
    // Append query params for search/filter endpoints.
    if (ep.queryParams && ep.queryParams.length > 0) {
      const qp = ep.queryParams
        .map(q => `${q.name}=${encodeURIComponent(this.queryValues[q.name] || '')}`)
        .join('&');
      url += `?${qp}`;
    }
    return url;
  }

  // Builds the final request from form input and sends it with the correct HTTP method.
  submit() {
    const ep = this.activeEndpoint();
    if (!ep) return;

    this.loading.set(true);
    this.response.set('');
    const url = this.buildUrl(ep);

    // GET/DELETE usually have no body, but write operations need one.
    let body: Record<string, unknown> | null = null;
    if (ep.bodyFields && ep.bodyFields.length > 0) {
      body = {};
      for (const field of ep.bodyFields) {
        const val = this.bodyValues[field.name];
        if (val !== undefined && val !== null && val !== '') {
          body[field.name] = field.type === 'number' ? Number(val) : val;
        }
      }

      // Some backend APIs take important IDs from the URL.
      // If the same field is also part of the request object, copy it into the body
      // so update flows stay aligned with backend expectations.
      for (const param of ep.params) {
        if (Object.prototype.hasOwnProperty.call(body, param.name)) {
          continue;
        }

        const val = this.paramValues[param.name];
        if (val !== undefined && val !== null && val !== '') {
          body[param.name] = param.type === 'number' ? Number(val) : val;
        }
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
        // Pretty-printing JSON makes success output easier to read during demos and testing.
        this.response.set(JSON.stringify(res, null, 2));
        this.responseStatus.set('success');
        this.loading.set(false);
      },
      error: (err: { error?: unknown; message?: string }) => {
        // The backend may return either plain text or structured JSON; handle both safely.
        const errBody = err.error || err.message || 'Request failed';
        this.response.set(typeof errBody === 'string' ? errBody : JSON.stringify(errBody, null, 2));
        this.responseStatus.set('error');
        this.loading.set(false);
      }
    });
  }

  // Every module page returns to the shared dashboard.
  back() {
    this.router.navigate(['/dashboard']);
  }
}
