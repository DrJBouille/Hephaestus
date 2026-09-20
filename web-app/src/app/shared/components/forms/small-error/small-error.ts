import { Component, input } from '@angular/core';

@Component({
  selector: 'app-small-error',
  imports: [],
  templateUrl: './small-error.html',
  styleUrl: './small-error.css',
})
export class SmallError {
  label = input<string>('');
}
