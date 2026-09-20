import { Routes } from '@angular/router';
import { authenticationGuard } from './core/guards/authentication/authentication-guard';
import { WorldsPage } from './features/worlds/worlds-page/worlds-page';
import { guestGuard } from './core/guards/guest/guest-guard';
import { LoginPage } from './features/authentication/login-page/login-page';

export const routes: Routes = [
  {
    path: 'login',
    canActivate: [guestGuard],
    component: LoginPage
  },
  {
    path: 'worlds',
    canActivate: [authenticationGuard],
    component: WorldsPage
  },
  {
    path: "",
    redirectTo: 'worlds',
    pathMatch: 'full'
  },
  {
    path: '*',
    redirectTo: 'worlds'
  }
];
