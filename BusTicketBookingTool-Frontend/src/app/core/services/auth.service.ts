import { Injectable, signal, computed } from '@angular/core';

/*
 * Beginner guide:
 * - This Angular service keeps API calls for this feature in one reusable place.
 * - Components call service methods instead of writing HttpClient requests directly in every screen.
 * - The service returns Observables, and the component subscribes to show success/error data in the UI.
 */
export interface AuthSession {
  username: string | null;
  password?: string; // Stored only so the interceptor can build the Basic Auth header.
  member?: string; // Display name used by the frontend.
}

@Injectable({ providedIn: 'root' })
// AuthService keeps the current login session in memory and sessionStorage.
export class AuthService {
  private sessionSignal = signal<AuthSession>({ username: null });

  // computed(...) creates read-only values that automatically update when the session changes.
  username = computed(() => this.sessionSignal().username);
  member = computed(() => this.sessionSignal().member);
  isAuth = computed(() => this.sessionSignal().username !== null);

  // Demo credentials that match the backend's in-memory Spring Security users.
  private validCredentials: { [key: string]: { password: string } } = {
    'vignesh': { password: 'vignesh123' },
    'nithish': { password: 'nithish123' },
    'aksha': { password: 'aksha123' },
    'ajitha': { password: 'ajitha123' },
    'priyadharshini': { password: 'priya123' }
  };

  constructor() { this.hydrate(); }

  // login() validates the chosen team member and stores the session for page refresh survival.
  login(username: string, pass: string): boolean {
    const key = username.toLowerCase();
    const cred = this.validCredentials[key];

    if (cred && cred.password === pass) {
      const session: AuthSession = {
        username: key,
        password: pass, // Stored so outgoing API calls can include the correct Basic Auth header.
        member: key
      };
      sessionStorage.setItem('authSession', JSON.stringify(session));
      this.sessionSignal.set(session);
      return true;
    }
    return false;
  }

  // logout() clears both browser storage and reactive state.
  logout(): void {
    sessionStorage.removeItem('authSession');
    this.sessionSignal.set({ username: null });
  }

  getUser(): string | null { return this.username(); }
  getPassword(): string | null { return this.sessionSignal().password ?? null; }

  // hydrate() restores the session after browser refresh.
  private hydrate(): void {
    const raw = sessionStorage.getItem('authSession');
    if (raw) {
      try { this.sessionSignal.set(JSON.parse(raw)); } catch {}
    }
  }
}
