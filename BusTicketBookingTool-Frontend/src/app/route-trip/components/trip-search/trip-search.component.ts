import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-trip-search',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './trip-search.component.html'
})
export class TripSearchComponent {}
