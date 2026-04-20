import { Injectable, signal, computed } from '@angular/core';

export interface AuthSession {
  username: string | null;
  member?: string; // Team member name
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private sessionSignal = signal<AuthSession>({ username: null });

  username = computed(() => this.sessionSignal().username);
  member = computed(() => this.sessionSignal().member);
  isAuth = computed(() => this.sessionSignal().username !== null);

  // Each team member's credentials
  private validCredentials: { [key: string]: { password: string } } = {
    'vignesh': { password: 'vignesh123' },
    'nithish': { password: 'nithish123' },
    'aksha': { password: 'aksha123' },
    'ajitha': { password: 'ajitha123' },
    'priyadharshini': { password: 'priya123' }
  };

  constructor() { this.hydrate(); }

  login(username: string, pass: string): boolean {
    const key = username.toLowerCase();
    const cred = this.validCredentials[key];

    if (cred && cred.password === pass) {
      const session: AuthSession = {
        username: key,
        member: key
      };
      sessionStorage.setItem('authSession', JSON.stringify(session));
      this.sessionSignal.set(session);
      return true;
    }
    return false;
  }

  logout(): void {
    sessionStorage.removeItem('authSession');
    this.sessionSignal.set({ username: null });
  }

  getUser(): string | null { return this.username(); }

  private hydrate(): void {
    const raw = sessionStorage.getItem('authSession');
    if (raw) {
      try { this.sessionSignal.set(JSON.parse(raw)); } catch {}
    }
  }
}
