import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { MODULE_CONFIGS } from '../../../core/config/module-endpoints.config';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
// This page shows the modules for the logged-in user.
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

  // Show only the modules that belong to this user.
  get visibleModules() {
    const user = this.auth.username()?.toLowerCase();
    if (!user) return [];
    return this.allModules.filter(m => m.owner.toLowerCase() === user);
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }
}
