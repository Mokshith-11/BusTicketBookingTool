import { Component } from '@angular/core';
import { ModuleEndpointsComponent } from '../../../shared/components/module-endpoints/module-endpoints.component';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [ModuleEndpointsComponent],
  templateUrl: './review-list.component.html'
})
/*
 * - This component controls the screen shown in the browser for this feature.
 * - The TypeScript file stores page state and user actions; the HTML file displays that state.
 * - When the user clicks buttons or submits forms, this component calls services or shared console logic.
 */
export class ReviewListComponent {}
