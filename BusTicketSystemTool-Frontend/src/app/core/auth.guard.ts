import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // Determine which user's sandbox is being accessed from the URL (e.g. /nithish/dashboard)
    const requestedUser = route.paramMap.get('user');
    
    if (requestedUser && this.authService.isLoggedInAs(requestedUser)) {
      return true;
    }
    
    // If not authorized for this specific user's route, force them to home
    this.router.navigate(['/home']);
    return false;
  }
}
