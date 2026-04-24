import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
/*
 * - This is the root component that holds the router outlet for the whole frontend.
 * - Angular swaps different pages inside this shell when the route changes.
 * - Think of it as the outer frame of the single-page application.
 */
export class AppComponent {
}
