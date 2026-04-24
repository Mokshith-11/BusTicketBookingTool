import { HttpInterceptorFn } from '@angular/common/http';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  const baseUrl = 'http://localhost:8081/api/v1';

  // Read credentials stored by AuthService during login
  let headers = req.headers.set('Content-Type', 'application/json');

  const raw = sessionStorage.getItem('authSession');
  if (raw) {
    try {
      const session = JSON.parse(raw);
      if (session?.username && session?.password) {
        // Build HTTP Basic Auth header: base64(username:password)
        const encoded = btoa(`${session.username}:${session.password}`);
        headers = headers.set('Authorization', `Basic ${encoded}`);
      }
    } catch {
      // session invalid, proceed without auth header
    }
  }

  const isAbsolute = req.url.startsWith('http://') || req.url.startsWith('https://');
  const url = isAbsolute ? req.url : `${baseUrl}${req.url.startsWith('/') ? '' : '/'}${req.url}`;

  return next(req.clone({ url, headers }));
};
