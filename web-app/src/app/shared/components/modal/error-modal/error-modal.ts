import { Component, input } from '@angular/core';
import { NormalText } from '../../text/normal-text/normal-text';
import { SmallText } from '../../text/small-text/small-text';

@Component({
  selector: 'app-error-modal',
  imports: [NormalText, SmallText],
  templateUrl: './error-modal.html',
  styleUrl: './error-modal.css',
})
export class ErrorModal {
  problemDetails = input<ProblemDetails>();
}
