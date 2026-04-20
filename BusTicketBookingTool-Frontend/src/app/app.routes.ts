import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

const endpointLoader = () => import('./features/module-endpoints/module-endpoints.component').then(m => m.ModuleEndpointsComponent);

export const routes: Routes = [
  // Landing
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent) },

  // Dashboard
  { path: 'dashboard', canActivate: [authGuard], loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent) },

  // Admin + Customer — all use the generic endpoints component
  { path: 'trips', canActivate: [authGuard], loadComponent: endpointLoader, data: { moduleKey: 'trips' } },
  { path: 'bookings', canActivate: [authGuard], loadComponent: endpointLoader, data: { moduleKey: 'bookings' } },
  { path: 'payments', canActivate: [authGuard], loadComponent: endpointLoader, data: { moduleKey: 'payments' } },
  { path: 'reviews', canActivate: [authGuard], loadComponent: endpointLoader, data: { moduleKey: 'reviews' } },

  // Admin-only
  { path: 'agencies', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'agencies' }, loadComponent: endpointLoader },
  { path: 'agency-offices', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'agency-offices' }, loadComponent: endpointLoader },
  { path: 'buses', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'buses' }, loadComponent: endpointLoader },
  { path: 'drivers', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'drivers' }, loadComponent: endpointLoader },
  { path: 'addresses', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'addresses' }, loadComponent: endpointLoader },
  { path: 'routes-mgmt', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'routes-mgmt' }, loadComponent: endpointLoader },
  { path: 'customers', canActivate: [authGuard, roleGuard], data: { roles: ['admin'], moduleKey: 'customers' }, loadComponent: endpointLoader },

  // Fallback
  { path: '**', redirectTo: '' }
];
