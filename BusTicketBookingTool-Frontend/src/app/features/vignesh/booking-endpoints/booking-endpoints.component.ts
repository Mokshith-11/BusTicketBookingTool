import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-booking-endpoints',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './booking-endpoints.component.html'
})
export class BookingEndpointsComponent { }
