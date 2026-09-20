import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlingService {
  private readonly _error = signal<ProblemDetails | null>(null);

  readonly error = this._error.asReadonly();

  private timeoutId?: ReturnType<typeof setTimeout>;

  set(problemDetails: ProblemDetails): void {
    this._error.set(problemDetails);

    if (this.timeoutId) {
      clearTimeout(this.timeoutId);
    }

    this.timeoutId = setTimeout(() => {
      this._error.set(null);
      this.timeoutId = undefined;
    }, 3000);
  }
}
