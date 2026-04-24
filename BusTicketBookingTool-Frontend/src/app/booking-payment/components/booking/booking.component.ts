import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './booking.component.html'
})
export class BookingComponent {}
