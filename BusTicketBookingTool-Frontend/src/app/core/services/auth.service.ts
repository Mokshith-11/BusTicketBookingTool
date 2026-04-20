import { Injectable, signal, computed } from '@angular/core';

export interface AuthSession {
  role: 'admin' | 'customer' | null;
  username: string | null;
  customerId?: number;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  
  // Base signaling strategy for global UI reactivity
  private sessionSignal = signal<AuthSession>({ role: null, username: null });

  // Computed derivatives
  role = computed(() => this.sessionSignal().role);
  username = computed(() => this.sessionSignal().username);
  customerId = computed(() => this.sessionSignal().customerId);
  isAuth = computed(() => this.sessionSignal().role !== null);

  constructor() {
    this.hydrate(); // Reload session from storage upon instantiation
  }

  login(username: string, pass: string): boolean {
    let session: AuthSession | null = null;
    
    // Explicit demo role-checking logic as per prompt
    if (username === 'admin' && pass === 'admin123') {
      session = { role: 'admin', username: 'admin' };
    } else if (username === 'customer' && pass === 'cust123') {
      session = { role: 'customer', username: 'customer', customerId: 1 };
    }

    if (session) {
      sessionStorage.setItem('authSession', JSON.stringify(session));
      this.sessionSignal.set(session);
      return true;
    }
    
    return false;
  }

  logout(): void {
    sessionStorage.removeItem('authSession');
    this.sessionSignal.set({ role: null, username: null });
  }

  private hydrate(): void {
    const raw = sessionStorage.getItem('authSession');
    if (raw) {
      this.sessionSignal.set(JSON.parse(raw));
    }
  }
}
