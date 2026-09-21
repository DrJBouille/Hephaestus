import { Component, inject } from '@angular/core';
import { ErrorHandlingService } from './core/services/error-handling-service/error-handling-service';
import { RouterOutlet } from '@angular/router';
import { ErrorModal } from './shared/components/modal/error-modal/error-modal';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ErrorModal],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private errorHandlingService = inject(ErrorHandlingService);

  error = this.errorHandlingService.error;
}
