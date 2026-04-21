import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { AuthService } from '../../../core/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginError = false;
  requestedUser: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.loginForm = this.fb.group({
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.requestedUser = params.get('user') || '';
    });
  }

  onLogin() {
    if (this.loginForm.valid && this.requestedUser) {
      if (this.authService.login(this.requestedUser, this.loginForm.value.password)) {
        this.loginError = false;
        this.router.navigate([`/${this.requestedUser}/dashboard`]);
      } else {
        this.loginError = true;
      }
    }
  }
}
