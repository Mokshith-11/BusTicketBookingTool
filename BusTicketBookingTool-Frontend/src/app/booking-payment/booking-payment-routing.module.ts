import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingComponent } from './components/booking/booking.component';
import { PaymentComponent } from './components/payment/payment.component';
import { SeatSelectionComponent } from './components/seat-selection/seat-selection.component';

const routes: Routes = [
  { path: 'booking', component: BookingComponent },
  { path: 'seat-selection', component: SeatSelectionComponent },
  { path: 'payment', component: PaymentComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingPaymentRoutingModule {}
