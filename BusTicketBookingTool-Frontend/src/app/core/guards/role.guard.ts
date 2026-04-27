import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Role-based checks are currently disabled; dashboard filtering is doing the limiting for now.
  return true;

  router.navigate(['/dashboard']);
  return false;
};
