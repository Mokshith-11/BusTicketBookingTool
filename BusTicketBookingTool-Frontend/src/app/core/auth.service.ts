import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedInUser: string | null = null;
  private token: string | null = null;

  login(username: string, password: string): boolean {
    const validUsers = ['vignesh', 'nithish', 'aksha', 'ajitha', 'priya'];
    
    // Check if user is in valid list
    // In a real app we would check password strictly. Here any password works for demo.
    if (validUsers.includes(username.toLowerCase())) {
      this.loggedInUser = username.toLowerCase();
      this.token = 'mock-jwt-token-' + this.loggedInUser; 
      localStorage.setItem('token', this.token);
      localStorage.setItem('currentUser', this.loggedInUser);
      return true;
    }
    return false;
  }

  logout() {
    this.loggedInUser = null;
    this.token = null;
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
  }

  isLoggedInAs(requestedUser: string): boolean {
    const current = this.loggedInUser || localStorage.getItem('currentUser');
    return current === requestedUser.toLowerCase();
  }

  getUser(): string | null {
    return this.loggedInUser || localStorage.getItem('currentUser');
  }
}
