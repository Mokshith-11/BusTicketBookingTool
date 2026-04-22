import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-customer-profile',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './customer-profile.component.html'
})
export class CustomerProfileComponent {}
