import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-trip',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './trip.component.html'
})
export class TripComponent {}
