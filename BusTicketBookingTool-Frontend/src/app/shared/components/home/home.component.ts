import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './home.component.html'
})
export class HomeComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  team = [
    { name: 'Vignesh', key: 'vignesh', modules: 'Booking & Payment', password: 'vignesh123' },
    { name: 'Nithish', key: 'nithish', modules: 'Bus & Driver', password: 'nithish123' },
    { name: 'Aksha', key: 'aksha', modules: 'Agency, AgencyOffice & Address', password: 'aksha123' },
    { name: 'Ajitha', key: 'ajitha', modules: 'Customer & Route', password: 'ajitha123' },
    { name: 'Priyadharshini', key: 'priyadharshini', modules: 'Trip & Review', password: 'priya123' }
  ];

  selectedMember = signal<any>(null);
  loginError = signal(false);

  loginForm = this.fb.group({
    username: [{ value: '', disabled: true }, Validators.required],
    password: ['', Validators.required]
  });

  selectMember(member: any) {
    this.selectedMember.set(member);
    this.loginForm.patchValue({ username: member.name });
    this.loginForm.get('password')?.reset();
    this.loginError.set(false);
  }

  onLogin() {
    const member = this.selectedMember();
    if (this.loginForm.valid && member) {
      const pass = this.loginForm.get('password')?.value;
      if (this.authService.login(member.key, pass as string)) {
        this.loginError.set(false);
        this.router.navigate(['/dashboard']);
      } else {
        this.loginError.set(true);
      }
    }
  }
}
