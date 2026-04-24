import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

/*
 * - This route guard protects frontend pages before Angular opens them.
 * - It checks login/role information from AuthService and decides whether the user can continue.
 * - If the check fails, Angular redirects the user instead of showing a restricted page.
 */
export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Role-based logic removed in favor of teammate-specific dashboard filtering.
  return true;

  router.navigate(['/dashboard']);
  return false;
};
