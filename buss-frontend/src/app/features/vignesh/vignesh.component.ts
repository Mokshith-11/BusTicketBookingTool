import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-vignesh',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './vignesh.component.html'
})
export class VigneshComponent {
  userName = this.authService.getUser();

  constructor(private authService: AuthService, private router: Router) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }
}
