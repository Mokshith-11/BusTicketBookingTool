import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-office',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './office.component.html'
})
export class OfficeComponent {}
