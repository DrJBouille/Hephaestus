import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthenticationService } from '../../services/authentication-service/authentication-service';

export const authenticationInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticationService = inject(AuthenticationService);
  const token = authenticationService.getAccesToken();

  if (!token) return next(req);

  return next(req.clone({
    setHeaders: {Authorization: `Bearer ${token}`}
  }));
};
