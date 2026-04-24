import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-review-form',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './review-form.component.html'
})
export class ReviewFormComponent {}
