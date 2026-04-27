import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Protected pages send unauthenticated users back to the home/login screen.
  if (!authService.isAuth()) {
    router.navigate(['/']);
    return false;
  }
  return true;
};
