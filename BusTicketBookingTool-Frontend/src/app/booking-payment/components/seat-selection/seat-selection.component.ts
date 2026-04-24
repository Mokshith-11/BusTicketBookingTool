import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-seat-selection',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './seat-selection.component.html'
})
export class SeatSelectionComponent {}
