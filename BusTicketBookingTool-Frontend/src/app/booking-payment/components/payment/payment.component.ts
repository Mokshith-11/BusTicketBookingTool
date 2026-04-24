import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './payment.component.html'
})
export class PaymentComponent {}
