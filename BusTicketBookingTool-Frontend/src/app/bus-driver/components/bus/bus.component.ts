import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-bus',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './bus.component.html'
})
export class BusComponent {}
