import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', loadComponent: () => import('./shared/components/home/home.component').then(m => m.HomeComponent) },
  { path: 'dashboard', canActivate: [authGuard], loadComponent: () => import('./shared/components/dashboard/dashboard.component').then(m => m.DashboardComponent) },

  { path: 'trips', canActivate: [authGuard], loadComponent: () => import('./route-trip/components/trip/trip.component').then(m => m.TripComponent) },
  { path: 'bookings', canActivate: [authGuard], loadComponent: () => import('./booking-payment/components/booking/booking.component').then(m => m.BookingComponent) },
  { path: 'payments', canActivate: [authGuard], loadComponent: () => import('./booking-payment/components/payment/payment.component').then(m => m.PaymentComponent) },
  { path: 'reviews', canActivate: [authGuard], loadComponent: () => import('./review/components/review-list/review-list.component').then(m => m.ReviewListComponent) },

  { path: 'agencies', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./agency/components/agency/agency.component').then(m => m.AgencyComponent) },
  { path: 'agency-offices', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./agency/components/office/office.component').then(m => m.OfficeComponent) },
  { path: 'addresses', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./agency/components/address/address.component').then(m => m.AddressComponent) },
  { path: 'buses', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./bus-driver/components/bus/bus.component').then(m => m.BusComponent) },
  { path: 'drivers', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./bus-driver/components/driver/driver.component').then(m => m.DriverComponent) },
  { path: 'routes-mgmt', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./route-trip/components/route/route.component').then(m => m.RouteComponent) },
  { path: 'customers', canActivate: [authGuard, roleGuard], data: { roles: ['admin'] }, loadComponent: () => import('./customer/components/customer-list/customer-list.component').then(m => m.CustomerListComponent) },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
/*
 * - This routing module decides which URL opens each component in this feature.
 * - It keeps navigation rules grouped with the feature so app-routing stays clean.
 * - Angular uses these routes when the browser path changes or when router.navigate is called.
 */
export class AppRoutingModule {}
