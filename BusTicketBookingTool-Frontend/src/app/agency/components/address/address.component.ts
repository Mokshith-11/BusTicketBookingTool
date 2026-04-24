import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-address',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './address.component.html'
})
export class AddressComponent {}
