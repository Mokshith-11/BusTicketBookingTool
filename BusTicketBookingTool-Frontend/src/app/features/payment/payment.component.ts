import { Component, inject, signal, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentService } from '../../core/services/payment.service';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './payment.component.html'
})
export class PaymentComponent implements OnInit {
  paymentService = inject(PaymentService);
  authService = inject(AuthService);
  fb = inject(FormBuilder);
  router = inject(Router);

  payments = signal<any[]>([]);

  payForm = this.fb.group({
    amount: ['', Validators.required],
    method: ['CARD', Validators.required]
  });

  ngOnInit() { this.loadPayments(); }

  loadPayments() {
    const custId = this.authService.customerId() || 1;
    this.paymentService.getCustomerPayments(custId).subscribe({
      next: (res) => this.payments.set(res?.data || []),
      error: () => this.payments.set([])
    });
  }

  onPay() {
    if (this.payForm.valid) {
      this.paymentService.createPayment(this.payForm.value).subscribe({
        next: () => { this.loadPayments(); this.payForm.reset(); },
        error: () => {}
      });
    }
  }

  back() { this.router.navigate(['/dashboard']); }
}
