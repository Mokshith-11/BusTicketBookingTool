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
/*
 * Beginner guide:
 * - This routing module decides which URL opens each component in this feature.
 * - It keeps navigation rules grouped with the feature so app-routing stays clean.
 * - Angular uses these routes when the browser path changes or when router.navigate is called.
 */
export class BookingPaymentRoutingModule {}
