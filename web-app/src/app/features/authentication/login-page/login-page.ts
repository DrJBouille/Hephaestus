import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormInput } from '../../../shared/components/forms/input/form-input';
import { Title } from '../../../shared/components/text/title/title';
import { Subtitle } from '../../../shared/components/text/subtitle/subtitle';
import { Button } from '../../../shared/components/forms/button/button';
import { AuthenticationService } from '../../../core/services/authentication-service/authentication-service';
import { ErrorHandlingService } from '../../../core/services/error-handling-service/error-handling-service';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, FormInput, Title, Subtitle, Button],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  private readonly formBuilder = inject(FormBuilder);
  private readonly authService = inject(AuthenticationService);
  private readonly errorHandlingService = inject(ErrorHandlingService);
  private readonly router = inject(Router);

  loading = signal(false);
  error = signal<string | null>(null);

  form = this.formBuilder.nonNullable.group({
    username: ['', [Validators.required]],
    password: ['', Validators.required],
  });

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.error.set(null);

    this.authService.login(this.form.getRawValue()).subscribe({
      next: () => {
        this.router.navigate(['/worlds']).then();
      },
      error: (err) => {
        this.errorHandlingService.set(err.error)
        this.error.set('Email ou mot de passe incorrect.');
        this.loading.set(false);
      },
    });
  }
}
