import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-agency',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './agency.component.html'
})
export class AgencyComponent {}
