import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { MODULE_CONFIGS } from '../../../core/config/module-endpoints.config';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html'
})
/*
 * Beginner guide:
 * - This component controls the screen shown in the browser for this feature.
 * - The TypeScript file stores page state and user actions; the HTML file displays that state.
 * - When the user clicks buttons or submits forms, this component calls services or shared console logic.
 */
export class DashboardComponent {
  auth = inject(AuthService);
  router = inject(Router);

  allModules = Object.entries(MODULE_CONFIGS).map(([key, config]) => ({
    name: config.moduleName,
    owner: config.owner,
    route: `/${key}`,
    endpoints: config.endpoints.length,
    description: config.description
  }));

  get visibleModules() {
    const user = this.auth.username()?.toLowerCase();
    if (!user) return [];
    return this.allModules.filter(m => m.owner.toLowerCase() === user);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
}
