import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

// Start the Angular app with the root component and shared app-level providers.
bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
