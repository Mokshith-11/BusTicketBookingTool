import { Component, Input, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../../../core/services/auth.service';
import { MODULE_CONFIGS, ModuleConfig, EndpointDef, EndpointParam } from '../../../core/config/module-endpoints.config';

@Component({
  selector: 'app-module-endpoints',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './module-endpoints.component.html'
})
// This component shows the API form for a module.
export class ModuleEndpointsComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private http = inject(HttpClient);
  private auth = inject(AuthService);

  // These values update the screen when they change.
  config = signal<ModuleConfig | null>(null);
  activeEndpoint = signal<EndpointDef | null>(null);
  paramValues: { [key: string]: string } = {};
  bodyValues: { [key: string]: string } = {};
  queryValues: { [key: string]: string } = {};
  fieldErrors: { [key: string]: string } = {};
  response = signal<string>('');
  responseStatus = signal<'success' | 'error' | ''>('');
  loading = signal(false);
  @Input() moduleKey?: string;

  // Load the selected module details when the page opens.
  ngOnInit() {
    const moduleKey = this.moduleKey || this.route.snapshot.data['moduleKey'];
    if (moduleKey && MODULE_CONFIGS[moduleKey]) {
      const conf = { ...MODULE_CONFIGS[moduleKey] };
      const user = this.auth.username()?.toLowerCase();
      // Mark the module if it belongs to the logged-in user.
      conf.isMine = conf.owner.toLowerCase() === user;
      this.config.set(conf);
    }
  }

  // Clear old values when the user picks a different API action.
  selectEndpoint(ep: EndpointDef) {
    this.activeEndpoint.set(ep);
    this.paramValues = {};
    this.bodyValues = {};
    this.queryValues = {};
    this.fieldErrors = {};
    this.response.set('');
    this.responseStatus.set('');
  }

  // Give each HTTP method a different color.
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

  // Replace values like {tripId} with what the user entered.
  buildUrl(ep: EndpointDef): string {
    let url = ep.path;
    // Fill path values.
    for (const param of ep.params) {
      url = url.replace(`{${param.name}}`, this.paramValues[param.name] || '0');
    }
    // Add query values for search APIs.
    if (ep.queryParams && ep.queryParams.length > 0) {
      const qp = ep.queryParams
        .map(q => `${q.name}=${encodeURIComponent(this.queryValues[q.name] || '')}`)
        .join('&');
      url += `?${qp}`;
    }
    return url;
  }

  // Make one key for each section so errors do not clash.
  fieldKey(section: 'param' | 'body' | 'query', name: string): string {
    return `${section}:${name}`;
  }

  // Show the current value for a field from the right form section.
  getFieldValue(section: 'param' | 'body' | 'query', name: string): string {
    if (section === 'param') return this.paramValues[name] || '';
    if (section === 'body') return this.bodyValues[name] || '';
    return this.queryValues[name] || '';
  }

  // Clear the error when the user starts fixing the field.
  clearFieldError(section: 'param' | 'body' | 'query', name: string) {
    delete this.fieldErrors[this.fieldKey(section, name)];
  }

  // Return the error text for one field.
  getFieldError(section: 'param' | 'body' | 'query', name: string): string {
    return this.fieldErrors[this.fieldKey(section, name)] || '';
  }

  // Highlight fields that still have an error.
  hasFieldError(section: 'param' | 'body' | 'query', name: string): boolean {
    return !!this.getFieldError(section, name);
  }

  // Check one field and return an easy message when something is wrong.
  validateField(field: EndpointParam, value: string, section: 'param' | 'body' | 'query'): string {
    const trimmed = value.trim();

    if (!trimmed) {
      return `${field.label} is required.`;
    }

    if (field.type === 'number' && Number.isNaN(Number(trimmed))) {
      return `${field.label} must be a number.`;
    }

    if ((field.name.toLowerCase().includes('email') || field.label.toLowerCase().includes('email')) &&
      !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(trimmed)) {
      return 'Enter a valid email address.';
    }

    if ((field.name.toLowerCase().includes('phone') || field.label.toLowerCase().includes('phone')) &&
      !/^\d{10}$/.test(trimmed)) {
      return `${field.label} must be 10 digits.`;
    }

    if (field.name === 'rating') {
      const rating = Number(trimmed);
      if (!Number.isInteger(rating) || rating < 1 || rating > 5) {
        return 'Rating must be between 1 and 5.';
      }
    }

    if (field.name === 'paymentStatus' || (section === 'query' && field.name === 'status')) {
      const status = trimmed.toLowerCase();
      if (status !== 'success' && status !== 'failed') {
        return `${field.label} must be Success or Failed.`;
      }
    }

    if (field.name === 'tripDate' || field.label.toLowerCase().includes('date')) {
      if (!/^\d{4}-\d{2}-\d{2}$/.test(trimmed)) {
        return `${field.label} must be in YYYY-MM-DD format.`;
      }
    }

    if ((field.name.toLowerCase().includes('time') || field.label.toLowerCase().includes('departure') || field.label.toLowerCase().includes('arrival')) &&
      !/^\d{4}-\d{2}-\d{2}[ tT]\d{2}:\d{2}(:\d{2})?$/.test(trimmed)) {
      return `${field.label} must be in YYYY-MM-DD HH:MM:SS format.`;
    }

    return '';
  }

  // Validate all visible fields before sending the request.
  validateActiveEndpoint(): boolean {
    const ep = this.activeEndpoint();
    if (!ep) return false;

    this.fieldErrors = {};
    const sections: Array<{ fields?: EndpointParam[]; section: 'param' | 'body' | 'query' }> = [
      { fields: ep.params, section: 'param' },
      { fields: ep.bodyFields, section: 'body' },
      { fields: ep.queryParams, section: 'query' }
    ];

    for (const { fields, section } of sections) {
      for (const field of fields || []) {
        const value = this.getFieldValue(section, field.name);
        const error = this.validateField(field, value, section);
        if (error) {
          this.fieldErrors[this.fieldKey(section, field.name)] = error;
        }
      }
    }

    return Object.keys(this.fieldErrors).length === 0;
  }

  // Build the request and send it.
  submit() {
    const ep = this.activeEndpoint();
    if (!ep) return;

    if (!this.validateActiveEndpoint()) {
      this.response.set(JSON.stringify({
        error: 'FrontendValidationError',
        message: 'Please fix the highlighted fields and try again.'
      }, null, 2));
      this.responseStatus.set('error');
      return;
    }

    this.loading.set(true);
    this.response.set('');
    const url = this.buildUrl(ep);

    // POST, PUT, and some PATCH calls need a body.
    let body: Record<string, unknown> | null = null;
    if (ep.bodyFields && ep.bodyFields.length > 0) {
      body = {};
      for (const field of ep.bodyFields) {
        const val = this.bodyValues[field.name];
        if (val !== undefined && val !== null && val !== '') {
          body[field.name] = field.type === 'number' ? Number(val) : val;
        }
      }

      // Some APIs need the same ID in both the URL and the body.
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
        // Show the response in a readable way.
        this.response.set(JSON.stringify(res, null, 2));
        this.responseStatus.set('success');
        this.loading.set(false);
      },
      error: (err: { error?: unknown; message?: string }) => {
        // Handle both text errors and JSON errors.
        const errBody = err.error || err.message || 'Request failed';
        this.response.set(typeof errBody === 'string' ? errBody : JSON.stringify(errBody, null, 2));
        this.responseStatus.set('error');
        this.loading.set(false);
      }
    });
  }

  // Go back to the dashboard.
  back() {
    this.router.navigate(['/dashboard']);
  }
}
