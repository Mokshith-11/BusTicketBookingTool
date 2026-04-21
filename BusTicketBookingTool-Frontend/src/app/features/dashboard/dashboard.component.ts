import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent {
  auth = inject(AuthService);
  router = inject(Router);

  allModules = [
    { name: 'Agency', owner: 'aksha', route: '/agencies', endpoints: 5 },
    { name: 'Agency Office', owner: 'aksha', route: '/agency-offices', endpoints: 5 },
    { name: 'Address', owner: 'aksha', route: '/addresses', endpoints: 2 },
    { name: 'Bus', owner: 'nithish', route: '/buses', endpoints: 5 },
    { name: 'Driver', owner: 'nithish', route: '/drivers', endpoints: 5 },
    { name: 'Route', owner: 'ajitha', route: '/routes-mgmt', endpoints: 5 },
    { name: 'Customer', owner: 'ajitha', route: '/customers', endpoints: 4 },
    { name: 'Trip', owner: 'priyadharshini', route: '/trips', endpoints: 6 },
    { name: 'Review', owner: 'priyadharshini', route: '/reviews', endpoints: 4 },
    { name: 'Booking', owner: 'vignesh', route: '/bookings', endpoints: 6 },
    { name: 'Payment', owner: 'vignesh', route: '/payments', endpoints: 5 }
  ];

  get visibleModules() {
    const user = this.auth.username()?.toLowerCase();
    if (!user) return [];
    // Case-insensitive match: backend config has 'Vignesh', login stores 'vignesh'
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
