import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './review-list.component.html'
})
export class ReviewListComponent {}
