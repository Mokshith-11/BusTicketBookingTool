import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-driver',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './driver.component.html'
})
export class DriverComponent {}
