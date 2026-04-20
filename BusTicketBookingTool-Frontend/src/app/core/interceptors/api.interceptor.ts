import { HttpInterceptorFn } from '@angular/common/http';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
  const baseUrl = 'http://localhost:8081/api/v1';

  // Hardcoding X-Role admin so the backend accepts all requests from any team member.
  let headers = req.headers.set('Content-Type', 'application/json');
  headers = headers.set('X-Role', 'admin');

  const isAbsolute = req.url.startsWith('http://') || req.url.startsWith('https://');
  const url = isAbsolute ? req.url : `${baseUrl}${req.url.startsWith('/') ? '' : '/'}${req.url}`;

  return next(req.clone({ url, headers }));
};
