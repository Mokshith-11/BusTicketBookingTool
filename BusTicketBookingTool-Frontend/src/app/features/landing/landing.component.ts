import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

// Angular Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatBadgeModule } from '@angular/material/badge';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, 
    MatCardModule, MatButtonModule, MatFormFieldModule, 
    MatInputModule, MatButtonToggleModule, MatIconModule,
    MatBadgeModule, MatToolbarModule
  ],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  loginForm = this.fb.group({
    roleType: ['admin', Validators.required],
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  loginError = signal<string | null>(null);

  modules = [
    { name: 'Agency', owner: 'Aksha', isMine: false },
    { name: 'Agency Office', owner: 'Aksha', isMine: false },
    { name: 'Address', owner: 'Aksha', isMine: false },
    { name: 'Bus', owner: 'Nithish', isMine: false },
    { name: 'Driver', owner: 'Nithish', isMine: false },
    { name: 'Route', owner: 'Ajitha', isMine: false },
    { name: 'Customer', owner: 'Ajitha', isMine: false },
    { name: 'Trip', owner: 'Priyadharshini', isMine: false },
    { name: 'Review', owner: 'Priyadharshini', isMine: false },
    { name: 'Booking', owner: 'Vignesh', isMine: true },
    { name: 'Payment', owner: 'Vignesh', isMine: true },
  ];

  team = [
    { name: 'Vignesh', modules: 'Booking, Payment', isMine: true },
    { name: 'Nithish', modules: 'Bus, Driver', isMine: false },
    { name: 'Aksha', modules: 'Agency, Agency Office, Address', isMine: false },
    { name: 'Ajitha', modules: 'Customer, Route', isMine: false },
    { name: 'Priyadharshini', modules: 'Trip, Review', isMine: false }
  ];

  onLogin() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      const success = this.auth.login(username as string, password as string);
      if (success) {
        this.loginError.set(null);
        this.router.navigate(['/dashboard']);
      } else {
        this.loginError.set('Invalid credentials. Please verify your role and password.');
      }
    }
  }
}
