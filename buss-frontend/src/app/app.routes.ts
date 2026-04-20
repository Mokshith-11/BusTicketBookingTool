import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { LoginComponent } from './features/auth/login/login.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { AuthGuard } from './core/auth.guard';
import { BookingEndpointsComponent } from './features/vignesh/booking-endpoints/booking-endpoints.component';
import { BookSeatComponent } from './features/vignesh/booking-endpoints/book-seat/book-seat.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  
  // Dynamic Route for Dedicated Login Page
  { path: ':user/login', component: LoginComponent },
  
  // Dynamic Route for Dedicated Dashboard (Protected)
  { 
    path: ':user/dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuard] 
  },
  
  // Vignesh specific sub-routes matching back to the sketch
  { 
    path: 'vignesh/booking-endpoints', 
    component: BookingEndpointsComponent,
    canActivate: [AuthGuard] 
  },
  { 
    path: 'vignesh/booking-endpoints/book-seat', 
    component: BookSeatComponent,
    canActivate: [AuthGuard] 
  },
  
  { path: '**', redirectTo: '/home' }
];
