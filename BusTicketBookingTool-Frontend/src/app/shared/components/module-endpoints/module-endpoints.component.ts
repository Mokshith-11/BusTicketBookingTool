import { Component, HostListener, Input, OnInit, inject, signal } from '@angular/core';
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
  lookupOptions = signal<LookupOption[]>([]);
  lookupLoading = signal(false);
  lookupError = signal('');
  openLookupField = signal<string | null>(null);
  lookupCache: Record<string, LookupOption[]> = {};
  readableResponse = signal<ReadableResponse | null>(null);
  response = signal<string>('');
  responseStatus = signal<'success' | 'error' | ''>('');
  outputView = signal<'preview' | 'table' | 'json'>('preview');
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
    this.openLookupField.set(null);
    this.lookupOptions.set([]);
    this.lookupError.set('');
    this.lookupCache = {};
    this.readableResponse.set(null);
    this.response.set('');
    this.responseStatus.set('');
    this.outputView.set('preview');
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

  // Some ID fields can use a dropdown instead of typing the number by hand.
  isLookupField(field: EndpointParam): boolean {
    return !!this.buildLookupRequest(field);
  }

  // Some screens can load helper dropdowns for their ID fields.
  endpointHasLookupField(ep: EndpointDef): boolean {
    return [...ep.params, ...(ep.bodyFields || []), ...(ep.queryParams || [])]
      .some(field => this.isLookupField(field));
  }

  // Show the current value for a field from the right form section.
  getFieldValue(section: 'param' | 'body' | 'query', name: string): string {
    if (section === 'param') return this.paramValues[name] || '';
    if (section === 'body') return this.bodyValues[name] || '';
    return this.queryValues[name] || '';
  }

  // Use browser pickers for backend date and date-time fields.
  getInputType(field: EndpointParam): string {
    return field.type;
  }

  // Show a hint that matches the backend format.
  getFieldPlaceholder(field: EndpointParam, fallback: string): string {
    if (field.name === 'departureTime' || field.name === 'arrivalTime') {
      return 'YYYY-MM-DDTHH:MM:SS.000Z';
    }

    if (field.name === 'tripDate' || field.type === 'date') {
      return 'YYYY-MM-DD';
    }

    return fallback;
  }

  // Clear the error when the user starts fixing the field.
  clearFieldError(section: 'param' | 'body' | 'query', name: string) {
    delete this.fieldErrors[this.fieldKey(section, name)];
  }

  // When the user types in a lookup field, keep the dropdown open and filter the list.
  onFieldInput(section: 'param' | 'body' | 'query', field: EndpointParam) {
    this.clearFieldError(section, field.name);

    if (!this.isLookupField(field)) {
      return;
    }

    this.openLookupField.set(this.fieldKey(section, field.name));
    this.loadLookupOptions(field);
  }

  // Return the error text for one field.
  getFieldError(section: 'param' | 'body' | 'query', name: string): string {
    return this.fieldErrors[this.fieldKey(section, name)] || '';
  }

  // Highlight fields that still have an error.
  hasFieldError(section: 'param' | 'body' | 'query', name: string): boolean {
    return !!this.getFieldError(section, name);
  }

  // Disable submit until every visible field has a valid value.
  isSubmitDisabled(): boolean {
    const ep = this.activeEndpoint();
    if (!ep || this.loading()) {
      return true;
    }

    const sections: Array<{ fields?: EndpointParam[]; section: 'param' | 'body' | 'query' }> = [
      { fields: ep.params, section: 'param' },
      { fields: ep.bodyFields, section: 'body' },
      { fields: ep.queryParams, section: 'query' }
    ];

    for (const { fields, section } of sections) {
      for (const field of fields || []) {
        const value = this.getFieldValue(section, field.name);
        if (this.validateField(field, value, section)) {
          return true;
        }
      }
    }

    return false;
  }

  // Switch between readable preview, table view, and raw JSON.
  setOutputView(view: 'preview' | 'table' | 'json') {
    this.outputView.set(view);
  }

  // Table view only makes sense when we have rows to show.
  hasTableView(): boolean {
    return !!this.readableResponse()?.table?.length;
  }

  // The dropdown is shown only for the active lookup field.
  isLookupDropdownOpen(section: 'param' | 'body' | 'query', name: string): boolean {
    return this.openLookupField() === this.fieldKey(section, name);
  }

  // Show all options first, then narrow them down while the user types.
  getLookupSuggestions(section: 'param' | 'body' | 'query', name: string): LookupOption[] {
    const value = this.getFieldValue(section, name).trim().toLowerCase();
    const options = this.lookupOptions();

    if (!value) {
      return options;
    }

    return options.filter(option => {
      return option.value.toLowerCase().includes(value) || option.label.toLowerCase().includes(value);
    });
  }

  // Keep the option text easy to scan in the dropdown.
  lookupLabel(option: LookupOption): string {
    return option.label;
  }

  // Put the chosen ID into the field and close the dropdown.
  selectLookupOption(section: 'param' | 'body' | 'query', name: string, option: LookupOption) {
    if (section === 'param') this.paramValues[name] = option.value;
    if (section === 'body') this.bodyValues[name] = option.value;
    if (section === 'query') this.queryValues[name] = option.value;

    this.clearFieldError(section, name);
    this.openLookupField.set(null);
  }

  // Close the dropdown when the user clicks anywhere else.
  @HostListener('document:click')
  closeLookupDropdown() {
    this.openLookupField.set(null);
  }

  // Stop outside clicks from closing the dropdown while the user is inside the field area.
  keepLookupDropdownOpen(event: Event, section: 'param' | 'body' | 'query', name: string) {
    event.stopPropagation();
    this.openLookupField.set(this.fieldKey(section, name));
  }

  // Open the lookup, clear old errors, and load fresh options for this field.
  activateLookup(section: 'param' | 'body' | 'query', field: EndpointParam, event?: Event) {
    event?.stopPropagation();
    this.clearFieldError(section, field.name);
    this.lookupError.set('');
    this.openLookupField.set(this.fieldKey(section, field.name));

    if (this.isLookupField(field)) {
      this.loadLookupOptions(field);
    }
  }

  // Find a value that another dropdown depends on, like agencyId before officeId.
  findSelectedValue(name: string): string {
    return this.paramValues[name] || this.bodyValues[name] || this.queryValues[name] || '';
  }

  // Some modules need a slightly different dropdown source for the same field name.
  getActiveModuleKey(): string {
    return this.moduleKey || this.route.snapshot.data['moduleKey'] || '';
  }

  // Read dynamic API fields safely from list results.
  readLookupValue(item: LookupSourceItem, key: string): string | number | boolean | null | undefined {
    return item[key];
  }

  // Convert browser date values to the format the backend expects.
  formatValueForRequest(field: EndpointParam, value: string): string {
    if (!value) {
      return value;
    }

    if (field.name === 'departureTime' || field.name === 'arrivalTime') {
      return value;
    }

    return value;
  }

  // Decide which API should power the dropdown for this field.
  buildLookupRequest(field: EndpointParam): LookupRequest | null {
    switch (field.name) {
      case 'agencyId':
        return {
          url: '/agencies',
          emptyMessage: 'No agencies found.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'agencyId')),
            label: `Agency ${this.readLookupValue(item, 'agencyId')} | ${this.readLookupValue(item, 'name') || 'No name'}`
          })
        };
      case 'routeId':
        return {
          url: '/routes',
          emptyMessage: 'No routes found.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'routeId')),
            label: `Route ${this.readLookupValue(item, 'routeId')} | ${this.readLookupValue(item, 'fromCity') || '-'} -> ${this.readLookupValue(item, 'toCity') || '-'}`
          })
        };
      case 'tripId':
        return {
          url: '/trips',
          emptyMessage: 'No trips found.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'tripId')),
            label: `Trip ${this.readLookupValue(item, 'tripId')} | ${this.readLookupValue(item, 'fromCity') || '-'} -> ${this.readLookupValue(item, 'toCity') || '-'} | ${this.readLookupValue(item, 'tripDate') || '-'} | ${this.readLookupValue(item, 'availableSeats') ?? 0} seats`
          })
        };
      case 'customerId':
        return {
          url: '/customers',
          emptyMessage: 'No customers found.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'customerId')),
            label: `Customer ${this.readLookupValue(item, 'customerId')} | ${this.readLookupValue(item, 'name') || 'No name'} | ${this.readLookupValue(item, 'phone') || 'No phone'}`
          })
        };
      case 'addressId':
      case 'officeAddressId':
      case 'boardingAddressId':
      case 'droppingAddressId':
        return {
          url: '/addresses',
          emptyMessage: 'No addresses found.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'addressId')),
            label: `Address ${this.readLookupValue(item, 'addressId')} | ${this.readLookupValue(item, 'address') || 'No address'} | ${this.readLookupValue(item, 'city') || 'No city'} | ${this.readLookupValue(item, 'state') || 'No state'}`
          })
        };
      case 'officeId': {
        const agencyId = this.findSelectedValue('agencyId');
        if (!agencyId) {
          return {
            url: '/offices',
            emptyMessage: 'No offices found.',
            map: (item) => ({
              value: String(this.readLookupValue(item, 'officeId')),
              label: `Office ${this.readLookupValue(item, 'officeId')} | ${this.readLookupValue(item, 'agencyName') || 'No agency'} | ${this.readLookupValue(item, 'officeMail') || 'No email'}`
            })
          };
        }
        return {
          url: `/agencies/${agencyId}/offices`,
          emptyMessage: 'No offices found for this agency.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'officeId')),
            label: `Office ${this.readLookupValue(item, 'officeId')} | ${this.readLookupValue(item, 'officeContactPersonName') || 'No contact'} | ${this.readLookupValue(item, 'officeMail') || 'No email'}`
          })
        };
      }
      case 'busId': {
        if (this.getActiveModuleKey() === 'trips') {
          return {
            url: '/buses',
            emptyMessage: 'No buses found.',
            map: (item) => ({
              value: String(this.readLookupValue(item, 'busId')),
              label: `Bus ${this.readLookupValue(item, 'busId')} | ${this.readLookupValue(item, 'registrationNumber') || 'No plate'} | ${this.readLookupValue(item, 'capacity') ?? 0} seats`
            })
          };
        }

        const officeId = this.findSelectedValue('officeId');
        if (!officeId) {
          return {
            url: '/buses',
            emptyMessage: 'No buses found.',
            map: (item) => ({
              value: String(this.readLookupValue(item, 'busId')),
              label: `Bus ${this.readLookupValue(item, 'busId')} | ${this.readLookupValue(item, 'registrationNumber') || 'No plate'} | ${this.readLookupValue(item, 'capacity') ?? 0} seats`
            })
          };
        }
        return {
          url: `/offices/${officeId}/buses`,
          emptyMessage: 'No buses found for this office.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'busId')),
            label: `Bus ${this.readLookupValue(item, 'busId')} | ${this.readLookupValue(item, 'registrationNumber') || 'No plate'} | ${this.readLookupValue(item, 'capacity') ?? 0} seats`
          })
        };
      }
      case 'driverId':
      case 'driver1Id':
      case 'driver2Id': {
        if (this.getActiveModuleKey() === 'trips') {
          return {
            url: '/drivers',
            emptyMessage: 'No drivers found.',
            map: (item) => ({
              value: String(this.readLookupValue(item, 'driverId')),
              label: `Driver ${this.readLookupValue(item, 'driverId')} | ${this.readLookupValue(item, 'name') || 'No name'} | ${this.readLookupValue(item, 'licenseNumber') || 'No license'}`
            })
          };
        }

        const officeId = this.findSelectedValue('officeId');
        if (!officeId) {
          return {
            url: '/drivers',
            emptyMessage: 'No drivers found.',
            map: (item) => ({
              value: String(this.readLookupValue(item, 'driverId')),
              label: `Driver ${this.readLookupValue(item, 'driverId')} | ${this.readLookupValue(item, 'name') || 'No name'} | ${this.readLookupValue(item, 'licenseNumber') || 'No license'}`
            })
          };
        }
        return {
          url: `/offices/${officeId}/drivers`,
          emptyMessage: 'No drivers found for this office.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'driverId')),
            label: `Driver ${this.readLookupValue(item, 'driverId')} | ${this.readLookupValue(item, 'name') || 'No name'} | ${this.readLookupValue(item, 'licenseNumber') || 'No license'}`
          })
        };
      }
      case 'bookingId': {
        const customerId = this.findSelectedValue('customerId');
        if (!customerId) {
          return { emptyMessage: 'Select Customer ID first.' };
        }
        return {
          url: `/customers/${customerId}/bookings`,
          emptyMessage: 'No bookings found for this customer.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'bookingId')),
            label: `Booking ${this.readLookupValue(item, 'bookingId')} | Trip ${this.readLookupValue(item, 'tripId') || '-'} | Seat ${this.readLookupValue(item, 'seatNumber') || '-'}`
          })
        };
      }
      case 'paymentId': {
        const bookingId = this.findSelectedValue('bookingId');
        if (!bookingId) {
          return { emptyMessage: 'Select Booking ID first.' };
        }
        return {
          url: `/payments/bookings/${bookingId}/payment`,
          emptyMessage: 'No payment found for this booking.',
          map: (item) => ({
            value: String(this.readLookupValue(item, 'paymentId')),
            label: `Payment ${this.readLookupValue(item, 'paymentId')} | ${this.readLookupValue(item, 'paymentStatus') || 'No status'} | ${this.readLookupValue(item, 'amount') ?? 0}`
          })
        };
      }
      default:
        return null;
    }
  }

  // Load list data for the current lookup field.
  loadLookupOptions(field: EndpointParam) {
    const lookup = this.buildLookupRequest(field);
    if (!lookup) {
      return;
    }

    if (!lookup.url) {
      this.lookupOptions.set([]);
      this.lookupError.set(lookup.emptyMessage || 'No options available.');
      return;
    }

    if (!lookup.map) {
      this.lookupOptions.set([]);
      this.lookupError.set('This list could not be prepared.');
      return;
    }

    if (lookup.url && this.lookupCache[lookup.url]) {
      this.lookupOptions.set(this.lookupCache[lookup.url]);
      this.lookupError.set('');
      return;
    }

    if (this.lookupLoading()) {
      return;
    }

    this.lookupLoading.set(true);
    this.lookupError.set('');
    this.lookupOptions.set([]);

    this.http.get<LookupApiResponse | LookupSourceItem[] | LookupSourceItem>(lookup.url).subscribe({
      next: (res) => {
        const raw: LookupSourceItem[] = Array.isArray(res)
          ? res as LookupSourceItem[]
          : Array.isArray(res.data)
            ? res.data as LookupSourceItem[]
            : res.data && typeof res.data === 'object'
              ? [res.data as LookupSourceItem]
              : [];

        const mapper = lookup.map!;
        const options = raw
          .map(item => mapper(item))
          .filter((item): item is LookupOption => !!item && !!item.value);

        if (lookup.url) {
          this.lookupCache[lookup.url] = options;
        }
        this.lookupOptions.set(options);
        this.lookupLoading.set(false);
        this.lookupError.set(options.length === 0 ? (lookup.emptyMessage || 'No options found.') : '');
      },
      error: (err: { status?: number; error?: unknown; message?: string }) => {
        this.lookupLoading.set(false);
        const backendMessage = typeof (err.error as { message?: unknown })?.message === 'string'
          ? (err.error as { message: string }).message
          : '';
        const statusText = err.status ? ` (status ${err.status})` : '';
        this.lookupError.set(backendMessage || `${field.label} list could not be loaded${statusText}.`);
      }
    });
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
      !/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}Z$/.test(trimmed)) {
      return `${field.label} must be in YYYY-MM-DDTHH:MM:SS.000Z format.`;
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

  // Seat APIs are easier to read with a small summary instead of a raw empty array.
  formatResponse(ep: EndpointDef, res: unknown): string {
    const formattedSeatResponse = this.formatSeatResponse(ep, res);
    if (formattedSeatResponse) {
      return formattedSeatResponse;
    }

    return JSON.stringify(res, null, 2);
  }

  // Build a simple card/table view from the API response.
  buildReadableResponse(res: unknown, status: 'success' | 'error'): ReadableResponse | null {
    if (typeof res !== 'object' || res === null) {
      return null;
    }

    const payload = res as Record<string, unknown>;
    const data = payload['data'];
    const timestamp = typeof payload['timestamp'] === 'string' ? payload['timestamp'] : '';
    const message = typeof payload['message'] === 'string' ? payload['message'] : '';
    const numericStatus = typeof payload['statusCode'] === 'number'
      ? payload['statusCode']
      : typeof payload['status'] === 'number'
        ? payload['status']
        : status === 'success' ? 200 : 500;

    const rows = this.getReadableRows(data);
    const details = this.getReadableDetails(payload, ['statusCode', 'status', 'message', 'timestamp', 'data']);

    return {
      banner: message || (status === 'success' ? 'Request completed successfully.' : 'Request failed.'),
      status: numericStatus,
      message,
      timestamp,
      details,
      table: rows
    };
  }

  // Show extra top-level fields in a small details strip.
  getReadableDetails(payload: Record<string, unknown>, skipKeys: string[]): Array<{ label: string; value: string }> {
    return Object.entries(payload)
      .filter(([key, value]) => !skipKeys.includes(key) && value !== null && value !== undefined && typeof value !== 'object')
      .map(([key, value]) => ({
        label: this.toTitleCase(key),
        value: String(value)
      }));
  }

  // Turn API data into rows for a simple result table.
  getReadableRows(data: unknown): ReadableRow[] | null {
    if (Array.isArray(data)) {
      const rows = data
        .filter((item): item is Record<string, unknown> => typeof item === 'object' && item !== null)
        .map(item => this.normalizeReadableRow(item));
      return rows.length > 0 ? rows : null;
    }

    if (typeof data === 'object' && data !== null) {
      return [this.normalizeReadableRow(data as Record<string, unknown>)];
    }

    return null;
  }

  // Keep the result values easy to render in a table.
  normalizeReadableRow(item: Record<string, unknown>): ReadableRow {
    const row: ReadableRow = {};

    for (const [key, value] of Object.entries(item)) {
      if (value === null || value === undefined) {
        row[this.toTitleCase(key)] = '-';
      } else if (typeof value === 'object') {
        row[this.toTitleCase(key)] = JSON.stringify(value);
      } else {
        row[this.toTitleCase(key)] = String(value);
      }
    }

    return row;
  }

  // Make backend field names look nicer in the result card.
  toTitleCase(value: string): string {
    return value
      .replace(/([A-Z])/g, ' $1')
      .replace(/[_-]/g, ' ')
      .replace(/^\w/, (char) => char.toUpperCase())
      .trim();
  }

  // Show seat lists in a friendlier way for booking screens.
  formatSeatResponse(ep: EndpointDef, res: unknown): string | null {
    if (!ep.path.includes('/seats/booked') && !ep.path.includes('/seats/available')) {
      return null;
    }

    const payload = typeof res === 'object' && res !== null ? res as Record<string, unknown> : null;
    const seats = Array.isArray(payload?.['data']) ? payload['data'] as Array<string | number> : null;

    if (!seats) {
      return null;
    }

    const isBookedView = ep.path.includes('/seats/booked');
    const message = typeof payload?.['message'] === 'string' ? payload['message'] : '';
    const tripId = this.paramValues['tripId'] || 'selected trip';

    if (seats.length === 0) {
      return JSON.stringify({
        statusCode: payload?.['statusCode'] ?? 200,
        message,
        tripId,
        summary: isBookedView ? 'No seats are booked for this trip.' : 'No available seats were found for this trip.',
        seats: []
      }, null, 2);
    }

    return JSON.stringify({
      statusCode: payload?.['statusCode'] ?? 200,
      message,
      tripId,
      summary: isBookedView
        ? `Booked seats: ${seats.join(', ')}`
        : `Available seats: ${seats.join(', ')}`,
      seats
    }, null, 2);
  }

  // Build the request and send it.
  submit() {
    const ep = this.activeEndpoint();
    if (!ep) return;

    if (!this.validateActiveEndpoint()) {
      this.readableResponse.set({
        banner: 'Please fix the highlighted fields and try again.',
        status: 400,
        message: 'Frontend validation failed.',
        timestamp: '',
        details: [],
        table: null
      });
      this.outputView.set('preview');
      this.response.set(JSON.stringify({
        error: 'FrontendValidationError',
        message: 'Please fix the highlighted fields and try again.'
      }, null, 2));
      this.responseStatus.set('error');
      return;
    }

    this.loading.set(true);
    this.readableResponse.set(null);
    this.response.set('');
    this.outputView.set('preview');
    const url = this.buildUrl(ep);

    // POST, PUT, and some PATCH calls need a body.
    let body: Record<string, unknown> | null = null;
    if (ep.bodyFields && ep.bodyFields.length > 0) {
      body = {};
      for (const field of ep.bodyFields) {
        const val = this.bodyValues[field.name];
        if (val !== undefined && val !== null && val !== '') {
          const formattedValue = this.formatValueForRequest(field, val);
          body[field.name] = field.type === 'number' ? Number(formattedValue) : formattedValue;
        }
      }

      // Some APIs need the same ID in both the URL and the body.
      for (const param of ep.params) {
        if (Object.prototype.hasOwnProperty.call(body, param.name)) {
          continue;
        }

        const val = this.paramValues[param.name];
        if (val !== undefined && val !== null && val !== '') {
          const formattedValue = this.formatValueForRequest(param, val);
          body[param.name] = param.type === 'number' ? Number(formattedValue) : formattedValue;
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
        this.readableResponse.set(this.buildReadableResponse(res, 'success'));
        this.outputView.set(this.hasTableView() ? 'table' : 'preview');
        this.response.set(this.formatResponse(ep, res));
        this.responseStatus.set('success');
        this.loading.set(false);
      },
      error: (err: { error?: unknown; message?: string }) => {
        // Handle both text errors and JSON errors.
        const errBody = err.error || err.message || 'Request failed';
        this.readableResponse.set(this.buildReadableResponse(
          typeof errBody === 'string' ? { message: errBody, status: 500 } : errBody,
          'error'
        ));
        this.outputView.set('preview');
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

interface LookupOption {
  value: string;
  label: string;
}

interface LookupSourceItem {
  [key: string]: string | number | boolean | null | undefined;
}

interface LookupRequest {
  url?: string;
  emptyMessage?: string;
  map?: (item: LookupSourceItem) => LookupOption;
}

interface LookupApiResponse {
  data?: LookupSourceItem[] | LookupSourceItem;
}

interface ReadableResponse {
  banner: string;
  status: number;
  message: string;
  timestamp: string;
  details: Array<{ label: string; value: string }>;
  table: ReadableRow[] | null;
}

interface ReadableRow {
  [key: string]: string;
}
