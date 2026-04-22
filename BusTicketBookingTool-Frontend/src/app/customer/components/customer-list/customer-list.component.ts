import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './customer-list.component.html'
})
export class CustomerListComponent {}
