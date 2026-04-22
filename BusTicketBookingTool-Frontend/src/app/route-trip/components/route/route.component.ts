import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-route',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './route.component.html'
})
export class RouteComponent {}
