import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html'
})
export class HomeComponent {
  users = ['Vignesh', 'Nithish', 'Priya', 'Ajitha', 'Aksha'];

  constructor(private router: Router) {}

  selectName(name: string) {
    this.router.navigate([`/${name.toLowerCase()}/login`]);
  }
}
